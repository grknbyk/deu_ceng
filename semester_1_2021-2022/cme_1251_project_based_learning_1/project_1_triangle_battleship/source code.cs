using System;

namespace triangle_battleship
{
    public class Program
    {
        public static void Main(string[] args)
        {

            //writes the menu for how to play
            Console.WriteLine(@"     -= TRIANGLE BATTLESHIP =-
      
             - MENU -
Please select from the options below
    1 – Enter ship location 
    2 – Ship info 
    3 – Shoot at the ship 
    4 – Show score table
    5 – Exit");

            //necessarry variables
            //corners of the ship
            int Ax = 0;
            int Ay = 0;
            int Bx = 0;
            int By = 0;
            int Cx = 0;
            int Cy = 0;
            //sides of the ship
            double a = 0;
            double b = 0;
            double c = 0;
            //perimeter and area of the ship
            double perimeter = 0;
            double area = 0;

            //coordinate system
            string coordinate_system = @"  12|                              
  11|                              
  10|                              
   9|                              
   8|                              
   7|                              
   6|                              
   5|                              
   4|                              
   3|                              
   2|                              
   1|                              
    +------------------------------
     123456789012345678901234567890";

            //score table
            string first_name = "Nazan Kaya", second_name = "Ali Kurt", third_name = "Sibel Arslan";
            double first_score = 60, second_score = 30, third_score = 10;


            while (true)
            {
                //option selection
                Console.WriteLine("\nPlease enter the option number:");
                string option = Console.ReadLine();

                if (option == "5")
                {
                    Console.WriteLine("\nExiting the game...");
                    break;
                }
                else if (option == "1")
                {
                    //reset the variables
                    //corners of the ship
                    Ax = 0;
                    Ay = 0;
                    Bx = 0;
                    By = 0;
                    Cx = 0;
                    Cy = 0;
                    //sides of the ship
                    a = 0;
                    b = 0;
                    c = 0;
                    //perimeter and area of the ship
                    perimeter = 0;
                    area = 0;

                    //coordinate system
                    coordinate_system = @"  12|                              
  11|                              
  10|                              
   9|                              
   8|                              
   7|                              
   6|                              
   5|                              
   4|                              
   3|                              
   2|                              
   1|                              
    +------------------------------
     123456789012345678901234567890";

                    //take the coordinates
                    Console.WriteLine("\nFirst coordinate Ax(Min 1 Max 30):");
                    Ax = Convert.ToInt32(Console.ReadLine());
                    Console.WriteLine("First coordinate Ay(Min 1 Max 12):");
                    Ay = Convert.ToInt32(Console.ReadLine());
                    Console.WriteLine("Second coordinate Bx(Min 1 Max 30):");
                    Bx = Convert.ToInt32(Console.ReadLine());
                    Console.WriteLine("Second coordinate By(Min 1 Max 12):");
                    By = Convert.ToInt32(Console.ReadLine());
                    Console.WriteLine("Third coordinate Cx(Min 1 Max 30):");
                    Cx = Convert.ToInt32(Console.ReadLine());
                    Console.WriteLine("Third coordinate Cy(Min 1 Max 12):");
                    Cy = Convert.ToInt32(Console.ReadLine());

                    //checking coordinates entered correcrtly
                    if ( (1 <= Ax && Ax <= 30 && 1 <= Bx && Bx <= 30 && 1 <= Cx && Cx <= 30 && 1 <= Ay && Ay <= 12 && 1 <= By && By <= 12 && 1 <= Cy && Cy <= 12) == false)
                    {
                        Console.WriteLine("Incorrect coordinates! Please enter a value between 1-30!");
                        continue;
                    }
					//continue if coordinates entered correctly
                    else
                    {
                        //calculating the sides of the triangle
                        a = Math.Pow(Math.Pow(Math.Abs(Bx - Cx), 2) + Math.Pow(Math.Abs(By - Cy), 2), 0.5);
                        b = Math.Pow(Math.Pow(Math.Abs(Ax - Cx), 2) + Math.Pow(Math.Abs(Ay - Cy), 2), 0.5);
                        c = Math.Pow(Math.Pow(Math.Abs(Bx - Ax), 2) + Math.Pow(Math.Abs(By - Ay), 2), 0.5);

                        //checking if it is a triangle
                        bool is_it_a_triangle = ((Math.Abs(b - c) < a && a < b + c) && (Math.Abs(a - c) < b && b < a + c) && (Math.Abs(b - a) < c && c < b + a));
                        if (is_it_a_triangle == false)
                        {
                            Console.WriteLine("\nThis coordinates can't make a triangle. Try Again!");
                            continue;
                        }

                        //calculating the position of dot in coordinate system
                        int index_A = ((37 * (12 - Ay)) + (Ax + 4));
                        int index_B = ((37 * (12 - By)) + (Bx + 4));
                        int index_C = ((37 * (12 - Cy)) + (Cx + 4));

                        //replacing the dots to the coordinate_system
                        coordinate_system = coordinate_system.Substring(0, index_A) + "A" + coordinate_system.Substring(index_A + 1);
                        coordinate_system = coordinate_system.Substring(0, index_B) + "B" + coordinate_system.Substring(index_B + 1);
                        coordinate_system = coordinate_system.Substring(0, index_C) + "C" + coordinate_system.Substring(index_C + 1);
                    }

                    

                }
				
				//ship info
                else if (option == "2")
                {
                    //checking whether coordinates are given
                    if (coordinate_system.Contains("A") == false)
                    {
                        Console.WriteLine("Please, first enter the coordinates!");
                        continue;
                    }
                    

                    //ship info
                    Console.WriteLine("\n" + coordinate_system);
                    
					Console.WriteLine("\nSHIP INFO");
                    
					Console.WriteLine("The ship corners: A(" + Ax + "," + Ay + ") B(" + Bx + "," + By + ") C(" + Cx + "," + Cy + ")");
                    
					Console.WriteLine("The sides of the ship: a=" + Math.Round(a, 2) + " b=" + Math.Round(b, 2) + " c=" + Math.Round(c, 2));

                    perimeter = a + b + c;
                    Console.WriteLine("The perimeter of the ship: " + Math.Round(perimeter, 2));

                    area = Math.Abs((((Ax * By) + (Bx * Cy) + (Cx * Ay)) - ((Bx * Ay) + (Cx * By) + (Ax * Cy))) / 2);
                    Console.WriteLine("The area of the ship: " + Math.Round(area, 2));

                    double angle_A = Math.Round(Math.Asin((area * 2) / (b * c)) * (180 / Math.PI), 2);
                    double angle_B = Math.Round(Math.Asin((area * 2) / (a * c)) * (180 / Math.PI), 2);
                    double angle_C = Math.Round(Math.Asin((area * 2) / (b * a)) * (180 / Math.PI), 2);
                    Console.WriteLine("The angles of the ship: A=" + angle_A + " B=" + angle_B + " C=" + angle_C);

                    double medianAB_x = (double)(Ax + Bx) / 2;
                    double medianAB_y = (double)(Ay + By) / 2;
                    double medianBC_x = (double)(Bx + Cx) / 2;
                    double medianBC_y = (double)(By + Cy) / 2;
                    double medianAC_x = (double)(Ax + Cx) / 2;
                    double medianAC_y = (double)(Ay + Cy) / 2;
                    Console.WriteLine("The median points: AB(" + medianAB_x + "," + medianAB_y + ") BC(" + medianBC_x + "," + medianBC_y + ") AC(" + medianAC_x + "," + medianAC_y + ")");

                    double Gx = Convert.ToDouble(Ax + Bx + Cx) / 3;
                    double Gy = Convert.ToDouble(Ay + By + Cy) / 3;
                    Console.WriteLine("The centroid of the ship: (" + Math.Round(Gx,2) + " , " + Math.Round(Gy,2) + ")");

                    double bisector_a = Math.Sqrt((b * c) - ((a * (b / (b + c))) * (a * (c / (b + c)))));
                    double bisector_b = Math.Sqrt((a * c) - ((b * (c / (a + c))) * (b * (a / (a + c)))));
                    double bisector_c = Math.Sqrt((b * a) - ((c * (b / (b + a))) * (c * (a / (b + a)))));
                    Console.WriteLine("The length of the bisectors: A=" + Math.Round(bisector_a, 2) + " B=" + Math.Round(bisector_b, 2) + " C=" + Math.Round(bisector_c, 2));

                    double u = (a + b + c) / 2;
                    double pi = Math.PI;
					
                    double The_area_of_the_inscribed_circle = pi * (area / u) * (area / u);
                    The_area_of_the_inscribed_circle = Math.Round(The_area_of_the_inscribed_circle, 2);
                    Console.WriteLine("The area of the inscribed circle: " + The_area_of_the_inscribed_circle);
                                       
                    double The_area_of_circumscribed_circle = pi * ((a * b * c) / (area * 4)) * ((a * b * c) / (area * 4));
                    The_area_of_circumscribed_circle = Math.Round(The_area_of_circumscribed_circle, 2);
                    Console.WriteLine("The area of circumscribed circle: " + The_area_of_circumscribed_circle);

                    string type_1 = "";
                    string type_2 = "";
                    if (a == b && b == c)
                        type_1 = "Equilaretal";
                    else if (a != b & b != c & c != a)
                        type_1 = "Scalane";
                    else
                        type_1 = "Isosceles";

                    if ((angle_A == 90) | (angle_B == 90) | (angle_C == 90))
                        type_2 = "(Right angled)";
                    else if ((angle_A > 90) | (angle_B > 90) | (angle_C > 90))
                        type_2 = "(Obtuse angled)";
                    else
                        type_2 = "(Acute angled)";
                    Console.WriteLine("The type of the ship: " + type_1 + " " + type_2);
                
                }
				
				//shooting
                else if (option == "3")
                {
                    //checks whether coordinates are given
                    if (coordinate_system.Contains("A") == false)
                    {
                        Console.WriteLine("Please, first enter the coordinates!");
                        continue;
                    }
                    //allows one shoot per game
                    else if (coordinate_system.Contains("X"))
                    {
                        Console.WriteLine("\n" + coordinate_system);
                        Console.WriteLine("\nYou can shoot only one per game!");
                        continue;
                    }

                    //generating shoot cordinates
                    Random rand = new Random();
                    int shoot_x = rand.Next(1, 30);
                    int shoot_y = rand.Next(1, 12);

                    //adding shoot(X) to the coordinate system
                    int index_shoot = ((37 * (12 - shoot_y)) + (shoot_x + 4));
                    coordinate_system = coordinate_system.Substring(0, index_shoot) + "X" + coordinate_system.Substring(index_shoot + 1);
                    Console.WriteLine("\n" + coordinate_system);

                    //writes the shoot coordinates
                    Console.WriteLine("\nShoot: (" + shoot_x + "," + shoot_y + ")");

                    //return a bool value if dot is in triangle
					//first step
                    double bx = Bx - Ax;
                    double by = By - Ay;
                    double cx = Cx - Ax;
                    double cy = Cy - Ay;
                    double x = shoot_x - Ax;
                    double y = shoot_y - Ay;

					//second step
                    double d = (bx * cy) - (cx * by);

					//third step
                    double WA = (x * (by - cy) + y * (cx - bx) + bx * cy - cx * by) / d;
                    double WB = (x * cy - y * cx) / d;
                    double WC = (y * bx - x * by) / d;
					
					//fourth step
                    bool value_WA = (0 <= WA && WA <= 1);
                    bool value_WB = (0 <= WB && WB <= 1);
                    bool value_WC = (0 <= WC && WC <= 1);
					
					//last step
                    bool ship_sink = (value_WA && value_WB && value_WC);

                    //return if ship sink
                    if (ship_sink != true)
                    {
                        Console.WriteLine("Your ship survived! Your total score is: " + Math.Round(area, 2));

                        //insert values to score table orderly in every case if player get high score
                        if (area > third_score)
                        {
                            Console.WriteLine("HIGH SCORE! Enter your nick:");
                            string winner = Console.ReadLine();

                            if (area > first_score)
                            {
                                first_name = winner;
                                first_score = area;
                            }
                            else if (area == first_score && area != second_score)
                            {
                                second_name = winner;
                                second_score = area;
                            }
                            else if (area > second_score)
                            {
                                second_name = winner;
                                second_score = area;
                            }
                            else if (area == second_score && area != third_score)
                            {
                                third_name = winner;
                                third_score = area;
                            }
                            else if (area > third_score)
                            {
                                third_name = winner;
                                third_score = area;
                            }
                        }
                    }
                    else
                    {
                        Console.WriteLine("Your ship sank!");
                    }
                }
		//score table
                else if (option == "4")
                {
                    Console.WriteLine("         SCORE TABLE");
                    Console.WriteLine(first_name + " " + Math.Round(first_score,2));
                    Console.WriteLine(second_name + " " + Math.Round(second_score, 2));
                    Console.WriteLine(third_name + " " + Math.Round(third_score, 2));
                }
				//other option inputs
                else Console.WriteLine("\nUnknown Option!");
            }
        }
    }
}