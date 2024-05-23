import csv
import tkinter as tk
from tkinter import ttk


class DiabetesPredictorGUI(tk.Tk):
    def __init__(self, CSV_FILE="diabetes.csv"):
        super().__init__()
        self.csv_file = CSV_FILE
        self.title("Diabetes Prediction App")
        self.geometry("+500+200")  # set the position of the window
        self.minsize(400, 300)
        ttk.Style().configure("TButton", padding=5)  # style for buttons
        ttk.Style().configure("TEntry", padding=3)  # style for entries
        ttk.Style().configure("TLabel", padding=2, font=("Guilded", 10))  # style for labels
        self.data = {}  # diabetes data
        self.input_boxes = []  # input fields for each column
        self.input_n = None  # input field for number of neighbors
        self.initialize_gui()  # initialize the GUI

    def initialize_gui(self):
        self.data = self.load_data_from_csv()
        self.add_input_fields()
        self.add_calculate_button()
        self.add_separator()
        self.add_output_label()
    
    def add_output_label(self):
        self.output_label = ttk.Label(self, text="Output") # label to display output
        self.output_label.pack(padx=5, pady=5)  # attach it to the parent (self)

    def add_calculate_button(self):
        button = ttk.Button(self, text="Calculate", style="TButton", command=self.calculate)  # calculate button
        button.pack(side=tk.TOP, padx=5, pady=5)  # attach it to parent

    def add_separator(self):
        separator = tk.Frame(self, height=1, bg="grey")  # create line
        separator.pack(fill=tk.X, padx=5, pady=5)  # attach it to parent (self)

    def add_input_fields(self):
        for key in self.data["columns"][:-1]:  # for each column excluding outcome
            label = ttk.Label(self, text=key)  # create label widget to indicate column
            entry = ttk.Entry(self, textvariable=tk.DoubleVar(value=0))  # create input widget
            label.pack(side=tk.TOP)  # attach label to parent (self)
            entry.pack(side=tk.TOP)  # attach entry to parent (self)
            self.input_boxes.append(entry)  # assign them into class variable to reuse
        label_n = ttk.Label(self, text="Number of Neighbors")  # create label widget to indicate column
        input_n = ttk.Entry(self, textvariable=tk.IntVar(value=5))  # create input widget for number of neighbors
        label_n.pack(side=tk.TOP)  # attach label to parent (self)
        input_n.pack(side=tk.TOP)  # attach entry to parent (self)
        self.input_n = input_n  # assign them into class variable to reuse

    def load_data_from_csv(self):
        headers, data_rows = self.parse_csv_file(self.csv_file)
        headers, data_rows, maxs, mins = self.preprocess_data(headers, data_rows)  # standardize data
        self.createPreprocessedCSV(headers, data_rows)  # make preprocessed csv
        data = {
            "columns": [header for header in headers],
            "max": maxs,  # maximum value for each column excluding outcome
            "min": mins,  # minimum value for each column excluding outcome
            "rows": data_rows,
        }
        return data

    def parse_csv_file(self, file_path):
        with open(file_path) as f:
            reader = csv.reader(f)
            headers = next(reader)
            data_rows = [row for row in reader]
        return headers, data_rows

    def preprocess_data(self, headers, data_rows):
        len_ = range(len(headers) - 1)  # -1 means, exclude the outcome column
        mins = [min([float(row[i]) for row in data_rows]) for i in len_]  # min value for each column
        maxs = [max([float(row[i]) for row in data_rows]) for i in len_]  # max value for each column
        rngs = [max_ - min_ for min_, max_ in zip(mins, maxs)]  # valid value range for each column
        rows = [
            [abs((float(row[i]) - mins[i]) / rngs[i]) for i in len_] + [int(row[-1])] for row in data_rows
        ]   # columns other than outcomes are normalized to [0, 1]   + outcome column
        return headers, rows, maxs, mins

    def createPreprocessedCSV(self, headers, data_rows):
        with open(self.csv_file.split(".")[0] + "_preprocessed.csv", "w") as f:  # file name with '_preprocessed.csv' postfix
            writer = csv.writer(f, lineterminator='\n')
            writer.writerow(headers)
            writer.writerows(data_rows)

    def calculate(self):
        try:
            input_data = [float(entry.get()) for entry in self.input_boxes]  # get inputs and convert to type float
            num_neighbors = int(self.input_n.get())  # get number of neighbors  
        except ValueError:  # if there is a value error
            self.output_label["text"] = "Invalid Input: please fill all fields with valid numbers"  # display error message
            return  # stop function to continue
        
        for idx, inpt in enumerate(input_data):  # validate the values
            mn = self.data["min"][idx]  # min value for that column
            mx = self.data["max"][idx]  # max value for that column
            if mn <= inpt and inpt <= mx:  # if it is in valid range
                input_data[idx] = (inpt - mn) / (mx - mn)  # standardize
            else:  # else display error message to inform user
                self.output_label["text"] = f"Invalid {self.data['columns'][idx]}: valid range [{mn} , {mx}]"
                return  # stop function to continue
            
        num_of_rows = len(self.data["rows"])  # number of rows in the data
        if num_neighbors > num_of_rows or num_neighbors < 1:  # if number of neighbors is invalid
            self.output_label["text"] = f"Invalid numbers of neighbors: valid range [1 , {num_of_rows}]"
            return  # stop function to continue

        rows_without_outcome = [row[:-1] for row in self.data["rows"]]
        distances = [(idx, self.eucDist(input_data, row)) for idx, row in enumerate(rows_without_outcome)]  # calculate distances
        closest = sorted(distances, key=lambda x: x[1])[:num_neighbors]  # get n closest points
        o_idx = self.data["columns"].index("Outcome")  # index of the outcome column
        outcomes = [self.data["rows"][r_idx][o_idx] for r_idx, _ in closest]  # get closests' outcomes
        print(*[f'row:{idx}, out:{out}' for idx,out in zip([x[0] for x in closest],outcomes)], sep=" | ")  # print the closest idx, outcomes
        ratio = sum(outcomes) / len(outcomes)
        self.output_label["text"] = (f"Diabetes Probability: {ratio * 100:.2f}%")  # display probability

    # Euclidean distance
    def eucDist(self, point1, point2):
        """Euclidean distance between two points."""
        distance = 0
        for x in range(len(point1)):
            distance += (point1[x] - point2[x]) ** 2
        return distance**0.5


if __name__ == "__main__":
    DiabetesPredictorGUI().mainloop()