using System;

namespace Calender
{
    class Program
    {
        static void Main(string[] args)
        {
            //month name nicely (april to April)
            string month_nicely(string name)
            {
                return name.Substring(0, 1).ToUpper() + name.Substring(1);
            }

            //if first date is older than the second
            bool older_date(int d1, int m1, int y1, int d2, int m2, int y2)
            {
                bool last_value = false;
                if (y1 < y2) last_value = true;
                else if (y1 == y2)
                {
                    if (m1 < m2) last_value = true;
                    else if (m1 == m2 && d1 <= d2) last_value = true;
                }
                return last_value;
            }

            //checks the year wheter is a leap year
            bool is_leap_year(int year)
            {
                int last_two_digits = year % 100;
                if (year % 400 == 0) return true;
                else if (last_two_digits % 4 == 0) return true;
                else return false;
            }

            //calculate days in a month
            string months_30 = ("april,june,september,november");
            int day_in_month(string month, int YEAR)
            {
                if (month == "february")
                {
                    if (is_leap_year(YEAR)) return 29;
                    else return 28;
                }
                else if (months_30.Contains(month)) return 30;
                else return 31;
            }

            //month in which season
            string month_season(int no)
            {
                string name = "";
                switch (no)
                {
                    case 3:
                    case 4:
                    case 5:
                        name = "Spring";
                        break;
                    case 6:
                    case 7:
                    case 8:
                        name = "Summer";
                        break;
                    case 9:
                    case 10:
                    case 11:
                        name = "Autumn";
                        break;
                    case 12:
                    case 1:
                    case 2:
                        name = "Winter";
                        break;
                }
                return name;
            }

            //verify the month name (afa = false, april = true)
            bool month_check(string name)
            {
                bool month_che = false;
                switch (name)
                {
                    case "december":
                    case "november":
                    case "october":
                    case "september":
                    case "august":
                    case "july":
                    case "june":
                    case "may":
                    case "april":
                    case "march":
                    case "february":
                    case "january":
                        month_che = true;
                        break;
                }
                return month_che;
            }

            //month name to month no
            int month_name_to_number(string month_name)
            {
                int no = 0;
                switch (month_name)
                {
                    case "january":
                        no = 1;
                        break;
                    case "february":
                        no = 2;
                        break;
                    case "march":
                        no = 3;
                        break;
                    case "april":
                        no = 4;
                        break;
                    case "may":
                        no = 5;
                        break;
                    case "june":
                        no = 6;
                        break;
                    case "july":
                        no = 7;
                        break;
                    case "august":
                        no = 8;
                        break;
                    case "september":
                        no = 9;
                        break;
                    case "october":
                        no = 10;
                        break;
                    case "november":
                        no = 11;
                        break;
                    case "december":
                        no = 12;
                        break;
                }
                return no;
            }

            //month no to month name
            string month_number_to_name(int no)
            {
                string name = "";
                switch (no)
                {
                    case 1:
                        name = "january";
                        break;
                    case 2:
                        name = "february";
                        break;
                    case 3:
                        name = "march";
                        break;
                    case 4:
                        name = "april";
                        break;
                    case 5:
                        name = "may";
                        break;
                    case 6:
                        name = "june";
                        break;
                    case 7:
                        name = "july";
                        break;
                    case 8:
                        name = "august";
                        break;
                    case 9:
                        name = "september";
                        break;
                    case 10:
                        name = "october";
                        break;
                    case 11:
                        name = "november";
                        break;
                    case 12:
                        name = "december";
                        break;
                }
                return name;
            }

            //return quotient ( like // operator in python ) (used in day_of_the_date)
            int quotient(int divedend, int divisor)
            {
                return (divedend - (divedend % divisor)) / divisor;
            }

            //determine the day of the week
            string day_of_the_date(int Day, string Month, int Year)
            {
                int total = 0;
                string day_of_the_week = "";

                total += Day;

                switch (Month)
                {
                    case "january":
                    case "october":
                        total += 0;
                        break;
                    case "february":
                    case "march":
                    case "november":
                        total += 3;
                        break;
                    case "april":
                    case "july":
                        total += 6;
                        break;
                    case "may":
                        total += 1;
                        break;
                    case "june":
                        total += 4;
                        break;
                    case "august":
                        total += 2;
                        break;
                    case "september":
                    case "december":
                        total += 5;
                        break;
                }

                if (is_leap_year(Year) && (Month == "january" || Month == "february")) total-=1;

                total = (total + (Year % 100) + quotient((Year % 100),4) + 6) % 7;

                switch (total)
                {
                    case 0:
                        day_of_the_week = "Sunday";
                        break;
                    case 1:
                        day_of_the_week = "Monday";
                        break;
                    case 2:
                        day_of_the_week = "Tuesday";
                        break;
                    case 3:
                        day_of_the_week = "Wednesday";
                        break;
                    case 4:
                        day_of_the_week = "Thursday";
                        break;
                    case 5:
                        day_of_the_week = "Friday";
                        break;
                    case 6:
                        day_of_the_week = "Saturday";
                        break;
                } 
                return day_of_the_week;
            }

            Console.WriteLine("Enter for the continue. \nEnter 'q' for the exit");
            while (Console.ReadLine() != "q") {

            //taking the inputs
            Console.WriteLine("D1:");
            var D1_var = Console.ReadLine();
            Console.WriteLine("M1:");
            string M1 = (Console.ReadLine()).ToLower().Replace("ı","i");
            Console.WriteLine("Y1:");
            var Y1_var = Console.ReadLine();
            Console.WriteLine("D2:");
            var D2_var = Console.ReadLine();
            Console.WriteLine("M2:");
            string M2 = (Console.ReadLine()).ToLower().Replace("ı","i");
            Console.WriteLine("Y2:");
            var Y2_var = Console.ReadLine();
            Console.WriteLine("n:");
            var n_var = Console.ReadLine();
            Console.WriteLine();

            //necessary variables
            int D1, D2, Y1, Y2, n;

            //bool values for whether inputs are correct
            bool success_1 = int.TryParse(Y1_var, out Y1);
            bool success_2 = int.TryParse(Y2_var, out Y2);
            bool success_3 = int.TryParse(D1_var, out D1);
            bool success_4 = int.TryParse(D2_var, out D2);
            bool success_5 = int.TryParse(n_var, out n);
            bool success_6 = month_check(M1);
            bool success_7 = month_check(M2);
            bool success_8 = Y1 >= 2015 && Y1 < 2100;
            bool success_9 = Y2 >= 2015 && Y1 < 2100;
            bool success_10 = D1 >= 1 && D1 <= day_in_month(M1, Y1);
            bool success_11 = D2 >= 1 && D2 <= day_in_month(M2, Y2);
            bool success_12 = n > 0;

            //checks the inputs converted to appropriate types
            if (success_1 && success_2 && success_3 && success_4 && success_5)
            {
                //checks the inputs are in correct values
                if (success_6 && success_7 && success_8 && success_9 && success_10 && success_11 && success_12)
                {
                    //writes dates in a order by n value
                    int day_1, month_1, year_1, day_2, month_2, year_2;
                    string season;
                    if (older_date(D1, month_name_to_number(M1), Y1, D2, month_name_to_number(M2), Y2))
                    {
                        day_1 = D1;
                        month_1 = month_name_to_number(M1);
                        year_1 = Y1;

                        day_2 = D2;
                        month_2 = month_name_to_number(M2);
                        year_2 = Y2;
                    }
                    else
                    {
                        day_1 = D2;
                        month_1 = month_name_to_number(M2);
                        year_1 = Y2;
                        day_2 = D1;
                        month_2 = month_name_to_number(M1);
                        year_2 = Y1;
                    }
                    season = month_season(month_1);
                    Console.WriteLine(season);
                    while (older_date(day_1, month_1, year_1, day_2, month_2, year_2))
                    {
                        if (season != month_season(month_1))
                        {
                            Console.WriteLine("\n" + month_season(month_1));
                            season = month_season(month_1);
                        }
                        Console.WriteLine(day_1 + " " + month_nicely(month_number_to_name(month_1)) + " " + year_1 + " " + day_of_the_date(day_1, month_number_to_name(month_1), year_1));
                        if (month_number_to_name(month_1) == "december" && day_1 + n > 31)
                        {
                            year_1 += 1;
                            month_1 = 1;
                            day_1 = (day_1 + n) - 31;
                        }
                        else if (day_1 + n > day_in_month(month_number_to_name(month_1), year_1))
                        {
                            day_1 = (day_1 + n) - day_in_month(month_number_to_name(month_1), year_1);
                            month_1 += 1;
                        }
                        else day_1 += n;
                    }
                }

                //writes error messages beacues of the incorrect values
                else
                {
                    if (!success_10) Console.WriteLine("D1 is wrong! Incorrect value, enter a day in the month!");
                    if (!success_11) Console.WriteLine("D2 is wrong! Incorrect value, enter a day in the month!");
                    if (!success_6)  Console.WriteLine("M1 is wrong! Enter a month name!");
                    if (!success_7)  Console.WriteLine("M2 is wrong! Enter a month name!");
                    if (!success_8)  Console.WriteLine("Y1 is wrong! Incorrect value, enter a number between 2015 and 2099 !");
                    if (!success_9)  Console.WriteLine("Y2 is wrong! Incorrect value, enter a number between 2015 and 2099 !");
                    if (!success_12) Console.WriteLine("n  is wrong! Enter a positive number!");
                }
            }

            //writes error messages beacues of datatype non-covertible values
            else
            {
                if (!success_3) Console.WriteLine("D1 is wrong! Enter value in integer format!");
                if (!success_4) Console.WriteLine("D2 is wrong! Enter value in integer format!");
                if (!success_6) Console.WriteLine("M1 is wrong! Enter a month name!");
                if (!success_7) Console.WriteLine("M2 is wrong! Enter a month name!");
                if (!success_1) Console.WriteLine("Y1 is wrong! Enter value in integer format!");
                if (!success_2) Console.WriteLine("Y2 is wrong! Enter value in integer format!");
                if (!success_5) Console.WriteLine("n  is wrong! Enter value in integer format!");
            }
            }
        }
    }
}