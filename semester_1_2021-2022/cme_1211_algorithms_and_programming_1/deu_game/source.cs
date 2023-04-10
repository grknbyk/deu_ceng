using System;

namespace deu
{
    class Program
    {
        static void Main(string[] args)
        {
            //necessary variables
            int cursor_y = -1;
            Random random = new Random();
            string[] A1 = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
            string[] A2 = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
            string[] A3 = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
            string[] names = { "Derya", "Elife", "Fatih", "Ali", "Azra", "Sibel", "Cem", "Nazan", "Mehmet", "Nil", "Can", "Tarkan" };
            int[] scores = { 100, 100, 95, 90, 85, 80, 80, 70, 55, 50, 30, 30 };
            int player1 = 120, player2 = 120;
            int player_no = 0;
            int player_score = 0;

            //returns first empty index of a string array
            int first_empty_index(int row_no)
            {
                int index = 0;
                switch (row_no)
                {
                    case 1:
                        foreach (string item in A1)
                        {
                            if (item == "") break;
                            else
                            {
                                index++;
                                continue;
                            }
                        }
                        break;
                    case 2:
                        foreach (string item in A2)
                        {
                            if (item == "") break;
                            else
                            {
                                index++;
                                continue;
                            }
                        }
                        break;
                    case 3:
                        foreach (string item in A3)
                        {
                            if (item == "") break;
                            else
                            {
                                index++;
                                continue;
                            }
                        }
                        break;
                }
                return index;
            }

            //generates random letter then add it into a random row(A1,A2,A3)
            int first = 1, last = 4;
            void letter_generator()
            {
                int rndm_letter = random.Next(1, 4);
                string temp_letter = "";
                switch (rndm_letter)
                {
                    case 1:
                        temp_letter = "D";
                        break;
                    case 2:
                        temp_letter = "E";
                        break;
                    case 3:
                        temp_letter = "U";
                        break;
                }

                for (int i = 1; i <= 1; i++)
                {
                    int rndm_row = random.Next(first,last);
                    if (rndm_row == 1 && A1[14] == "") A1[first_empty_index(1)] = temp_letter;
                    else if (rndm_row == 2 && A2[14] == "") A2[first_empty_index(2)] = temp_letter; 
                    else if (rndm_row == 3 && A3[14] == "") A3[first_empty_index(3)] = temp_letter;
                    else i--;

                    //edit the random ranges for append a letter to connected squares
                    if (rndm_row == 2) { first = 1; last = 4; }
                    else if (rndm_row == 1 && A3[0] == "") { first = 1; last = 3; }
                    else if (rndm_row == 3 && A1[0] == "") { first = 2; last = 3; }
                }

            }

            //variables to keep the cursor coordinate values for coloring
            int[] letter_D = new int[2];
            int[] letter_E = new int[2];
            int[] letter_U = new int[2];

            //checks the deu and if any combination of deu occur returns true and assign the cursor coordinate values to variables above
            bool check_deu()
            {
                bool deu = false;
                int a = 0, b = 0, c = 0;
                while (a < 15 && b < 15 && c < 15)
                {
                    //combinations
                    if (A1[a] == "D" && A2[b] == "E" && A3[c] == "U") 
                    {
                        letter_D[0] = 3;
                        letter_D[1] = a;
                        letter_E[0] = 2;
                        letter_E[1] = b;
                        letter_U[0] = 1;
                        letter_U[1] = c;
                        deu = true; 
                        break; 
                    }
                    else if (A1[a] == "U" && A2[b] == "E" && A3[c] == "D")
                    {
                        letter_D[0] = 1;
                        letter_D[1] = c;
                        letter_E[0] = 2;
                        letter_E[1] = b;
                        letter_U[0] = 3;
                        letter_U[1] = a;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A1[a] == "U" && A2[b + 1] == "E" && A3[c + 2] == "D")
                    {
                        letter_D[0] = 1;
                        letter_D[1] = c+2;
                        letter_E[0] = 2;
                        letter_E[1] = b+1;
                        letter_U[0] = 3;
                        letter_U[1] = a;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A1[a] == "D" && A2[b + 1] == "E" && A3[c + 2] == "U")
                    {
                        letter_D[0] = 3;
                        letter_D[1] = a;
                        letter_E[0] = 2;
                        letter_E[1] = b + 1;
                        letter_U[0] = 1;
                        letter_U[1] = c + 2;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && a + 2 < 15 && A1[a + 2] == "U" && A2[b + 1] == "E" && A3[c] == "D")
                    {
                        letter_D[0] = 1;
                        letter_D[1] = c;
                        letter_E[0] = 2;
                        letter_E[1] = b + 1;
                        letter_U[0] = 3;
                        letter_U[1] = a + 2;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && a + 2 < 15 && A1[a + 2] == "D" && A2[b + 1] == "E" && A3[c] == "U")
                    {
                        letter_D[0] = 3;
                        letter_D[1] = a + 2;
                        letter_E[0] = 2;
                        letter_E[1] = b + 1;
                        letter_U[0] = 1;
                        letter_U[1] = c;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A1[a] == "D" && A1[b + 1] == "E" && A1[c + 2] == "U")
                    {
                        letter_D[0] = 3;
                        letter_D[1] = a;
                        letter_E[0] = 3;
                        letter_E[1] = b + 1;
                        letter_U[0] = 3;
                        letter_U[1] = c + 2;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A2[a] == "D" && A2[b + 1] == "E" && A2[c + 2] == "U")
                    {
                        letter_D[0] = 2;
                        letter_D[1] = a;
                        letter_E[0] = 2;
                        letter_E[1] = b + 1;
                        letter_U[0] = 2;
                        letter_U[1] = c + 2;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A3[a] == "D" && A3[b + 1] == "E" && A3[c + 2] == "U")
                    {
                        letter_D[0] = 1;
                        letter_D[1] = a;
                        letter_E[0] = 1;
                        letter_E[1] = b + 1;
                        letter_U[0] = 1;
                        letter_U[1] = c + 2;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A1[a] == "U" && A1[b + 1] == "E" && A1[c + 2] == "D")
                    {
                        letter_D[0] = 3;
                        letter_D[1] = c + 2;
                        letter_E[0] = 3;
                        letter_E[1] = b + 1;
                        letter_U[0] = 3;
                        letter_U[1] = a;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A2[a] == "U" && A2[b + 1] == "E" && A2[c + 2] == "D")
                    {
                        letter_D[0] = 2;
                        letter_D[1] = c + 2;
                        letter_E[0] = 2;
                        letter_E[1] = b + 1;
                        letter_U[0] = 2;
                        letter_U[1] = a;
                        deu = true;
                        break;
                    }
                    else if (b + 1 < 15 && c + 2 < 15 && A3[a] == "U" && A3[b + 1] == "E" && A3[c + 2] == "D")
                    {
                        letter_D[0] = 1;
                        letter_D[1] = c + 2;
                        letter_E[0] = 1;
                        letter_E[1] = b + 1;
                        letter_U[0] = 1;
                        letter_U[1] = a;
                        deu = true;
                        break;
                    }
                    else 
                    {
                        a++;
                        b++;
                        c++;
                    }
                }
                return deu;
            }

            //gameplay
            while (true)
            {
                //generating letter function above
                letter_generator();

                //decreases player's score 5 points each move
                switch ((player_no % 2 + 1))
                {
                    case 1:
                        player1 -= 5;
                        break;
                    case 2:
                        player2 -= 5;
                        break;
                }

                //gameboard
                Console.WriteLine("Player" + (player_no%2+1) + ":     (P1-" + player1 + " P2-" + player2 + ")");
                Console.Write("A1 ");
                foreach (var item in A1)
                {
                    Console.Write(item + " ");
                }
                Console.WriteLine();
                Console.Write("A2 ");
                foreach (var item in A2)
                {
                    Console.Write(item + " ");
                }
                Console.WriteLine();
                Console.Write("A3 ");
                foreach (var item in A3)
                {
                    Console.Write(item + " ");
                }
                Console.WriteLine();

                cursor_y += 5;
                player_no++; //changes the player

                if (check_deu()) //to see game tie quickly make check_deu() false. Do not forget change it back check_deu()
                {
                    //writing winner and highlighted DEU combination
                    Console.ForegroundColor = ConsoleColor.Black;
                    Console.BackgroundColor = ConsoleColor.DarkYellow;
                    Console.SetCursorPosition((letter_D[1] + 1) * 2 + 1, cursor_y - letter_D[0]);
                    Console.WriteLine("D");
                    Console.SetCursorPosition((letter_E[1] + 1) * 2 + 1, cursor_y - letter_E[0]);
                    Console.WriteLine("E");
                    Console.SetCursorPosition((letter_U[1] + 1) * 2 + 1 , cursor_y - letter_U[0]);
                    Console.WriteLine("U");
                    Console.ResetColor();
                    Console.SetCursorPosition(0, cursor_y);
                    Console.WriteLine("Winner: Player" + ((player_no - 1) % 2 + 1));

                    //determines winner no
                    player_no = ((player_no - 1) % 2 + 1);

                    //assigns winner score to player_score variable
                    if (player_no == 1) player_score = player1;
                    else if (player_no == 2) player_score = player2;

                    //reedit the score table 
                    for (int i = 0; i <=11; i++)
                    {
                        if (player_score > scores[i])
                        {
                            for (int a = 11; a > i; a--)
                            {
                                names[a] = names[a-1];
                                scores[a] = scores[a-1];
                            }
                            names[i] = "Player" + Convert.ToString(player_no);
                            scores[i] = player_score;
                            break;
                        }
                        else continue;
                    }
                    Console.WriteLine();
                    cursor_y += 2;
                    break;
                }

                //writes if game tie
                else if ((A1[14] != "" && A2[14] != "" && A3[14] != "") || (player1 == 0 && player2 == 0))
                {
                    Console.WriteLine("Tie");
                    Console.WriteLine();
                    cursor_y += 2;
                    break;
                }
                Console.ReadLine();
            }

            //writes score table
            Console.WriteLine("NAME         SCORE");
            for (int i = 0; i <= 11; i++)
            {
                Console.WriteLine(names[i]);
                Console.SetCursorPosition(13, cursor_y+1);
                Console.WriteLine(scores[i]);
                cursor_y += 1;
            }
            Console.ReadLine();
        }
    }
}