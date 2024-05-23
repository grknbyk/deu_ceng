import tkinter as tk
from threading import Thread
from time import sleep, strftime
from tkinter import ttk

from scservo_sdk import *
from serial.tools import list_ports


class ServoControl(tk.Tk):

    
    def __init__(self):
        super().__init__()
        self.title("Servo Motor Control Tool")
        self.MINIMUM_POSITION_VALUE  = 10          
        self.MAXIMUM_POSITION_VALUE  = 1000
        self.MOVING_PART             = 2400 // 5
        self.geometry("+400+100")  # set the position of the window
        self.logBox: tk.Text = None
        self.portList: ttk.Combobox = None
        self.baudrateList: ttk.Combobox = None
        self.portHandler: PortHandler = None
        self.packet_handler: scscl = None
        self.sensor_values_values = {}
        self.MotorList: ttk.Treeview = ttk.Treeview()
        self.DegreeSlider = None
        self.DelayEntry = None
        self.degree_var = tk.IntVar()
        self.speed_var = tk.IntVar()
        self.minsize(300, 300)
        self.initialize_gui()  # initialize the GUI

    def initialize_gui(self):
        frame0 = tk.Frame(self, highlightbackground="black", highlightthickness=1)
        frame0.grid(row=0, column=0, padx=5, pady=5, rowspan=2)

        SearchMotorBtn = ttk.Button(
            frame0,
            text="Search Motors",
            command=lambda: (
                self.write_log("Search Motors Button Clicked!"),
                Thread(target=self.search_motors).start(),
            ),
        )
        SearchMotorBtn.pack(padx=5, pady=5)

        MotorListLabel = ttk.Label(frame0, text="Motor List")
        MotorListLabel.pack(padx=5, pady=5)

        MotorLst = ttk.Treeview(frame0, columns=("ID", "Model"), show="headings", selectmode="browse")
        MotorLst.heading("ID", text="ID")
        MotorLst.heading("Model", text="Model")
        MotorLst.column("ID", width=40)
        MotorLst.column("Model", width=100)
        MotorLst.pack(padx=5, pady=5)
        self.MotorList = MotorLst
        
        self.MotorList.bind("<ButtonRelease-1>", lambda e: Thread(target=self.update_sensor_values).start())
                                
        frame1 = tk.Frame(self, highlightbackground="black", highlightthickness=1)
        frame1.grid(row=0, column=1, padx=5, pady=5)

        frame1HeadLine = ttk.Label(frame1, text="Connection", anchor="center", width=35)
        frame1HeadLine.grid(row=0, column=0, columnspan=2)

        portsLabel = ttk.Label(frame1, text="Ports:")
        portsLabel.grid(row=1, column=0, padx=5, pady=5)

        self.portList = ttk.Combobox(frame1, state="readonly", width=8)
        self.portList.grid(row=1, column=1, padx=5, pady=5)

        refresh_button = ttk.Button(
            frame1,
            text="Refresh Ports",
            command=lambda: (
                self.write_log("Refresh Button Clicked!"),
                self.refresh_ports(),
            ),
        )
        refresh_button.grid(row=3, column=0, padx=5, pady=5)

        baudrateLabel = ttk.Label(frame1, text="Baudrate:", width=10)
        baudrateLabel.grid(row=2, column=0, padx=5, pady=5)

        self.baudrateList = ttk.Combobox(frame1, state="readonly", width=8)
        self.baudrateList["values"] = (
            1000000,
            500000,
            250000,
            128000,
            115200,
            76800,
            57600,
            38400,
        )
        self.baudrateList.current(0)
        self.baudrateList.grid(row=2, column=1, padx=5, pady=5)

        self.baudrateList.bind(
            "<<ComboboxSelected>>",
            lambda e: self.write_log(f"Baudrate selected: {self.baudrateList.get()}"),
        )

        OpenPortBtn = ttk.Button(
            frame1,
            text="Open Port",
        )
        OpenPortBtn.grid(row=3, column=1, padx=5, pady=5)

        def open_port_click(event):
            text = OpenPortBtn.cget("text")
            self.write_log(f"{text} button clicked.")
            if text == "Open Port" and self.open_port():
                OpenPortBtn.config(text="Close Port")
            if text == "Close Port":
                self.portHandler.closePort()
                self.packet_handler = None
                self.write_log("Port closed.")
                OpenPortBtn.config(text="Open Port")

        OpenPortBtn.bind("<Button-1>", open_port_click)

        frame2 = tk.Frame(self, highlightbackground="black", highlightthickness=1)
        frame2.grid(row=1, column=1, padx=5, pady=5)

        frame2HeadLine = ttk.Label(frame2, text="Sensor Values", anchor="center")
        frame2HeadLine.grid(row=0, column=0, columnspan=4)

        Headline = ttk.Label(frame2, text="Motor ID:")
        Headline.grid(row=1, column=0, columnspan=2, padx=5, pady=5)

        sensor_labels = [
            ["Voltage", "Current", "Temperature", "Torque"],
            ["State", "Moving", "Speed", "Position"],
        ]
        for i, sublist in enumerate(sensor_labels):
            for j, label in enumerate(sublist):
                label_widget = ttk.Label(frame2, text=label + ":", anchor="e")
                label_widget.grid(row=j + 2, column=0 + (i * 2), pady=5)
                value_widget = ttk.Label(frame2, text="0", anchor="w", width=5)
                self.sensor_values_values[label] = value_widget
                value_widget.grid(row=j + 2, column=1 + (i * 2), padx=5, pady=5)

        frame3 = tk.Frame(self, highlightbackground="black", highlightthickness=1)
        frame3.grid(row=2, column=0, padx=5, pady=5, columnspan=2)

        frame3Headline = ttk.Label(frame3, text="Rotation Values", anchor="center")
        frame3Headline.grid(row=0, column=0, columnspan=5)

        DegreeLabel = ttk.Label(frame3, text="Degree:")
        DegreeLabel.grid(row=1, column=0, padx=5, pady=5)

        DegreeSlider = ttk.Scale(
            frame3,
            from_= self.MINIMUM_POSITION_VALUE,
            to= self.MAXIMUM_POSITION_VALUE,
            orient=tk.HORIZONTAL,
            length=320,
        )
        DegreeSlider.set(100)
        self.degree_var.set(100)
        DegreeSlider.bind(
            "<ButtonRelease-1>",
            lambda e: (
                DegreeSlider.set(int(DegreeSlider.get())),
                self.degree_var.set(int(DegreeSlider.get())),
                self.rotate_degree(DegreeSlider.get()),
                self.write_log(f"Degree set to {DegreeSlider.get():.0f}"),
            ),
        )
        DegreeSlider.grid(row=1, column=1, padx=5, pady=5, columnspan=4)
        self.DegreeSlider = DegreeSlider

        GoalLabel = ttk.Label(frame3, text="Goal:")
        GoalLabel.grid(row=3, column=0, pady=3)
        StartEntry = ttk.Entry(frame3, width=8)
        StartEntry.insert(0, self.MINIMUM_POSITION_VALUE)
        StartEntry.grid(row=3, column=1, padx=5, pady=5)
        EndEntry = ttk.Entry(frame3, width=8, textvariable=self.degree_var)
        EndEntry.grid(row=3, column=2, padx=5, pady=5)

        SpeedLabel = ttk.Label(frame3, text="Speed:")
        SpeedLabel.grid(row=3, column=3, pady=3)

        SpeedSlider = tk.Scale(
            frame3,
            from_=1,
            to=5,
            orient=tk.HORIZONTAL,
            length=100,
            variable=self.speed_var,
            command=lambda e: self.write_log(f"Speed set to {SpeedSlider.get()}"),
        )
        SpeedSlider.grid(row=3, column=4, padx=5, pady=5)

        DelayLabel = ttk.Label(frame3, text="Delay (ms):")
        DelayEntry = ttk.Entry(frame3, width=10)
        DelayLabel.grid(row=4, column=2, pady=3)
        DelayEntry.grid(row=4, column=3, padx=5, pady=5)
        self.DelayEntry = DelayEntry
        self.DelayEntry.insert(0, 0)

        def delay_changed(event):
            try:
                value = max(0, int(DelayEntry.get()))
                DelayEntry.delete(0, tk.END)
                DelayEntry.insert(0, value)
            except ValueError:
                self.write_log("Invalid value entered for Delay Entry.")
                DelayEntry.delete(0, tk.END)
                return

        DelayEntry.bind("<KeyRelease>", delay_changed)

        RepeatLabel = ttk.Label(frame3, text="Repeat", anchor="e")
        CheckBoxState = tk.BooleanVar()
        RepeatCheckBox = tk.Checkbutton(
            frame3, anchor="w", variable=CheckBoxState, width=5
        )
        RepeatCheckBox.bind(
            "<Button-1>",
            lambda e: self.write_log(
                f"Repeat checkbox {'checked' if CheckBoxState.get() else 'unchecked'}"
            ),
        )
        RepeatLabel.grid(row=4, column=0, pady=3)
        RepeatCheckBox.grid(row=4, column=1, padx=5, pady=5)

        startBtn = ttk.Button(
            frame3,
            text="Start",
            width=10,
        )
        startBtn.grid(row=4, column=4, padx=5, pady=5)

        def start():
            if self.portHandler is None:
                self.write_log("Port handler is not initialized.")
                return False

            if self.packet_handler is None:
                self.write_log("Packet handler is not initialized.")
                return False

            motor_id = self.MotorList.selection()
            if not motor_id:
                self.write_log("No motor selected.")
                return False
            else:
                motor_id = self.MotorList.item(motor_id, "values")[0]
                motor_id = int(motor_id)

            try:
                startDegree = int(StartEntry.get())
                endDegree = int(EndEntry.get())
                if startDegree < self.MINIMUM_POSITION_VALUE or startDegree > self.MAXIMUM_POSITION_VALUE:
                    raise ValueError
                if endDegree < self.MINIMUM_POSITION_VALUE or endDegree > self.MAXIMUM_POSITION_VALUE:
                    raise ValueError
            except Exception as e:
                self.write_log("Invalid value entered for Start or End Position.")
                return False
            dly = int(self.DelayEntry.get()) / 1000
            startBtn.config(text="Stop")
            goal_position = (startDegree, endDegree,)

                
            speed = int(self.speed_var.get()) * self.MOVING_PART
            
            index = 1
            dmy = 0
            while True:
                if dmy % 2 == 0:
                    sleep(dly)
                self.packet_handler.WritePos(motor_id, goal_position[index], 0, speed)
                while True:
                    moving, scs_comm_result, scs_error = self.packet_handler.ReadMoving(motor_id)
                    if moving == 0:
                        break

                if not CheckBoxState.get():
                    startBtn.config(text="Start")
                    break
                else:
                    index = 0 if index == 1 else 1
                    dmy += 1
                    
                    
        t = Thread(target=start)

        def start_btn_handler():
            text = startBtn.cget("text")
            self.write_log(f"{text} button clicked.")
            if text == "Start":
                t = Thread(target=start)
                t.start()
            if text == "Stop":
                CheckBoxState.set(False)

        startBtn.bind("<Button-1>", lambda e: Thread(target=start_btn_handler).start())

        self.logBox = tk.Text(
            self, width=47, height=6, fg="black", font=("sans-serif", 11)
        )
        self.logBox.grid(row=3, column=0, columnspan=2, padx=3, pady=10)

        self.write_log("GUI initialized")

    def update_sensor_values(self):
        if self.packet_handler is None:
            self.write_log("Packet handler is not initialized.")
            return
        
        selected_items = self.MotorList.selection()
        current_motor_id = self.MotorList.item(selected_items, "values")[0]
        
        mtr_id = int(current_motor_id)
        flag = True
        while selected_items:
            scs_present_position, scs_present_speed, scs_comm_result, scs_error = self.packet_handler.ReadPosSpeed(mtr_id)
            moving_value, moving_result, moving_error = self.packet_handler.ReadMoving(mtr_id) 
            voltage_value, temp_result, temp_error = self.packet_handler.read1ByteTxRx(mtr_id, SCSCL_PRESENT_VOLTAGE)
            x1_value, temp_result, temp_error = self.packet_handler.read1ByteTxRx(mtr_id, SCSCL_PRESENT_CURRENT_H)
            x2_value, temp_result, temp_error = self.packet_handler.read1ByteTxRx(mtr_id, SCSCL_PRESENT_CURRENT_L)
            temp_value, temp_result, temp_error = self.packet_handler.read1ByteTxRx(mtr_id, SCSCL_PRESENT_TEMPERATURE)
            torque_value, temp_result, temp_error = self.packet_handler.read1ByteTxRx(mtr_id, SCSCL_TORQUE_ENABLE)
            self.sensor_values_values["Voltage"].config(text=f'{voltage_value//10}.{voltage_value%10} V')
            self.sensor_values_values["Current"].config(text=f"{x1_value}.{x2_value}")
            self.sensor_values_values["Temperature"].config(text=temp_value)
            self.sensor_values_values["Torque"].config(text=torque_value)
            self.sensor_values_values["State"].config(text=scs_comm_result)
            self.sensor_values_values["Moving"].config(text=moving_value)
            self.sensor_values_values["Speed"].config(text=scs_present_speed)
            self.sensor_values_values["Position"].config(text=scs_present_position)
            if flag:
                self.degree_var.set(scs_present_position)
                self.DegreeSlider.set(scs_present_position)
                flag = False


    def write_log(self, message: str):
        msg = f'{strftime("%H:%M:%S")} - {message}'
        self.logBox.insert(tk.END, "\n" + msg)
        self.logBox.see(tk.END)

    def refresh_ports(self):
        ports = [port.device for port in list_ports.comports()]
        self.portList["values"] = sorted(ports)
        self.write_log("Ports refreshed.")
        self.portList.current(0)
        self.MotorList.delete(*self.MotorList.get_children())
            

    def open_port(self):
        if self.portList.get() == "":
            self.write_log("First select a port to open.")
            return False

        if port := self.portList.get() is None:
            self.write_log("Port is not selected.")
            return False

        if baudrate := self.baudrateList.get() is None:
            self.write_log("Baudrate is not selected.")
            return False

        self.write_log(f"Opening port {self.portList.get()}")
        self.portHandler = PortHandler(self.portList.get())
        if flag1 := self.portHandler.openPort():
            self.write_log("Port opened successfully.")
        else:
            self.write_log("Failed to open port.")
            return False

        BAUDRATE = int(self.baudrateList.get())
        if flag2 := self.portHandler.setBaudRate(BAUDRATE):
            self.write_log(f"Baudrate set to {baudrate}")
        else:
            self.write_log("Failed to set baudrate.")
            return False

        if flag1 and flag2:
            self.packet_handler = scscl(self.portHandler)
            self.write_log("Packet handler initialized.")
            return True
        else:
            self.write_log("Failed to initialize packet handler.")
            return False

    def search_motors(self):
        if self.portHandler is None:
            self.write_log("Port handler is not initialized.")
            self.write_log("Please open a port first.")
            return

        if self.packet_handler is None:
            self.write_log("Packet handler is not initialized.")
            return

        self.write_log("Searching motors...")
        ids = (motor_id for motor_id in range(MAX_ID + 1))
        is_motor_found = False
        for motor_id in ids:
            scs_model_number, scs_comm_result, scs_error = self.packet_handler.ping(
                motor_id
            )
            if scs_comm_result == COMM_SUCCESS:
                is_motor_found = True
                str_motor_list = f"Motor ID: {motor_id}, Model Number: {scs_model_number}"
                self.write_log("Motors found: " + str_motor_list)
                self.MotorList.insert("", 'end', text=str_motor_list, values=(motor_id, scs_model_number))
        
        if is_motor_found:
            self.write_log("Motor list updated.")
        else:
            self.write_log("No motors found.")

    def rotate_degree(self, degree: int):
        if not all((self.portHandler, self.packet_handler, self.MotorList.selection())):
            return
        try:
            dly = int(self.DelayEntry.get()) / 1000
        except ValueError:
            self.write_log("Invalid value entered for Delay Entry.")
            return
        
        try:
            if degree < self.MINIMUM_POSITION_VALUE or degree > self.MAXIMUM_POSITION_VALUE:
                raise ValueError
        except ValueError:
            self.write_log("Invalid value entered for Degree.")
            return

        
        motor_id = self.MotorList.item(self.MotorList.selection(), "values")[0]
        motor_id = int(motor_id)
        speed = int(self.speed_var.get()) * self.MOVING_PART
        degree = int(degree)
        sleep(dly)
        sleep(0.2)
        self.packet_handler.WritePos(motor_id, degree, 0, speed)
        while self.packet_handler.ReadMoving(motor_id)[0]:
            continue
        print("Rotating motor to degree:", degree)


if __name__ == "__main__":
    ServoControl().mainloop()
