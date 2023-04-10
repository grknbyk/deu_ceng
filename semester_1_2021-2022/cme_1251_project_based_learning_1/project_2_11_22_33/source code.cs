using System;
using System.Threading;

namespace _11_22_33
{
    class Program
    {
        static void Main(string[] args)
        {
			//necesssary variables
			int numcursorx, numcursory, number, temporary;
			int cursorx = 2, cursory = 1, score = 0;
			ConsoleKeyInfo cki;
			int[] row1 = new int[32];
			int[] row2 = new int[32];
			int[] row3 = new int[32];
			Random random = new Random();

			//printing the gameboard
			Console.WriteLine(" +------------------------------+");
			Console.WriteLine(" |");
			Console.WriteLine(" |");
			Console.WriteLine(" |");
			Console.WriteLine(" +------------------------------+");
			Console.SetCursorPosition(32, 1);
			Console.WriteLine("|");
			Console.SetCursorPosition(32, 2);
			Console.WriteLine("|");
			Console.SetCursorPosition(32, 3);
			Console.WriteLine("|");

			//generating 30 random number between 1-3 and checks matching also prints and updates score
			void create_number(int len)
			{
				for (int i = 1; i <= len; i++)
				{
					//updates and prints score
					Console.SetCursorPosition(41, 1);
					Console.WriteLine("Score = " + score);
					Console.SetCursorPosition(cursorx, cursory);

					//generates number and checks matching. if digits match, it deletes and generate for each of them
					numcursorx = random.Next(2, 32);    //generate number for column
					numcursory = random.Next(1, 4);     //generate number for row
					number = random.Next(1, 4);         //generate number to print

					//conditions for first row
					if (numcursory == 1 && row1[numcursorx - 1] == 0)
					{
						if (row1[numcursorx - 2] == number && row1[numcursorx] == number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							row1[numcursorx - 2] = 0;
							row1[numcursorx] = 0;
							Console.WriteLine("   ");
							score += 15;
							i -= 3;     //decrease for each deleted digits to generate new digits
						}
						else if (row1[numcursorx - 2] != number && row1[numcursorx] == number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx, numcursory);
							row1[numcursorx] = 0;
							Console.WriteLine("  ");
							score += 10;
							i -= 2;     //decrease for each deleted digits to generate new digits
						}
						else if (row1[numcursorx - 2] == number && row1[numcursorx] != number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							row1[numcursorx - 2] = 0;
							Console.WriteLine("  ");
							score += 10;
							i -= 2;     //decrease for each deleted digits to generate new digits
						}
						else
						{
							row1[numcursorx - 1] = number;
							Console.SetCursorPosition(numcursorx, numcursory);
							Console.WriteLine(number);
						}

					}

					//conditions for second row
					else if (numcursory == 2 && row2[numcursorx - 1] == 0)
					{
						if (row2[numcursorx - 2] == number && row2[numcursorx] == number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							row2[numcursorx - 2] = 0;
							row2[numcursorx] = 0;
							Console.WriteLine("   ");
							score += 15;
							i -= 3;     //decrease for each deleted digits to generate new digits
						}
						else if (row2[numcursorx - 2] != number && row2[numcursorx] == number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx, numcursory);
							row2[numcursorx] = 0;
							Console.WriteLine("  ");
							score += 10;
							i -= 2;     //decrease for each deleted digits to generate new digits
						}
						else if (row2[numcursorx - 2] == number && row2[numcursorx] != number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							row2[numcursorx - 2] = 0;
							Console.WriteLine("  ");
							score += 10;
							i -= 2;     //decrease for each deleted digits to generate new digits
						}
						else
						{
							row2[numcursorx - 1] = number;
							Console.SetCursorPosition(numcursorx, numcursory);
							Console.WriteLine(number);
						}
					}

					//conditions for third row
					else if (numcursory == 3 && row3[numcursorx - 1] == 0)
					{
						if (row3[numcursorx - 2] == number && row3[numcursorx] == number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							row3[numcursorx - 2] = 0;
							row3[numcursorx] = 0;
							Console.WriteLine("   ");
							score += 15;
							i -= 3;     //decrease for each deleted digits to generate new digits
						}
						else if (row3[numcursorx - 2] != number && row3[numcursorx] == number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx, numcursory);
							row3[numcursorx] = 0;
							Console.WriteLine("  ");
							score += 10;
							i -= 2;     //decrease for each deleted digits to generate new digits
						}
						else if (row3[numcursorx - 2] == number && row3[numcursorx] != number)
						{
							Console.ForegroundColor = ConsoleColor.White;
							Console.BackgroundColor = ConsoleColor.DarkGreen;
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							Console.WriteLine(Convert.ToString(number) + Convert.ToString(number));
							Console.ResetColor();
							Thread.Sleep(1000);
							Console.SetCursorPosition(numcursorx - 1, numcursory);
							row3[numcursorx - 2] = 0;
							Console.WriteLine("  ");
							score += 10;
							i -= 2;     //decrease for each deleted digits to generate new digits
						}
						else
						{
							row3[numcursorx - 1] = number;
							Console.SetCursorPosition(numcursorx, numcursory);
							Console.WriteLine(number);
						}
					}

					//it makes the loop repeat and generate new random values until any condition get true
					else
					{
						i--;
						continue;
					}
				}
			}

			//the function above
			create_number(30);

			// checks wheter game over
			bool game_over()
			{
				bool check = true;
                for (int a = 1; a <= 30; a++)
                {
					if (														// if
						(row1[a] != 0 && row1[a - 1] == 0 && row1[a + 1] == 0)	// this condition
						||														// or
						(row2[a] != 0 && row2[a - 1] == 0 && row2[a + 1] == 0)	// this condition
						||														// or
						(row3[a] != 0 && row3[a - 1] == 0 && row3[a + 1] == 0)	// this condition
						)														// become true
					{
						check = false;											// game is not over (return false)
					}
				}
				return check;													// if check do not become false returns true and game is over!
			}

			//the game
			while (true)
			{
				if (Console.KeyAvailable)
				{
					cki = Console.ReadKey(true);

					//movement keys (arrows)
					if (cki.Key == ConsoleKey.RightArrow && cursorx < 31)
					{
						cursorx++;
					}

					else if (cki.Key == ConsoleKey.LeftArrow && cursorx > 2)
					{
						cursorx--;
					}

					else if (cki.Key == ConsoleKey.UpArrow && cursory > 1)
					{
						cursory--;
					}

					else if (cki.Key == ConsoleKey.DownArrow && cursory < 3)
					{
						cursory++;
					}

					// S key
					else if (cki.KeyChar == 115)
					{
						if (cursory == 1 && row1[cursorx - 1] != 0 && row1[cursorx - 2] == 0 && row1[cursorx] == 0)
						{
							if (row2[cursorx - 1] != 0)
							{
								continue;
							}
							else if (row2[cursorx - 1] == 0)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(" ");
								temporary = row1[cursorx - 1];
								row1[cursorx - 1] = 0;
								Console.SetCursorPosition(cursorx, 2);
								if (row2[cursorx - 2] == temporary && row2[cursorx] == temporary)
								{
									row2[cursorx - 2] = 0;
									row2[cursorx] = 0;
									cursory++;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("   ");
									Thread.Sleep(100);
									score += 15;
									create_number(3);
								}
								else if (row2[cursorx] == temporary)
								{
									row2[cursorx] = 0;
									cursory++;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else if (row2[cursorx - 2] == temporary)
								{
									row2[cursorx - 2] = 0;
									cursory++;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else
								{
									row2[cursorx - 1] = temporary;
									Console.WriteLine(row2[cursorx - 1]);
									cursory++;
								}
							}
						}
						else if (cursory == 2 && row2[cursorx - 1] != 0 && row2[cursorx - 2] == 0 && row2[cursorx] == 0)
						{
							if (row3[cursorx - 1] != 0)
							{
								continue;
							}
							else if (row3[cursorx - 1] == 0)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(" ");
								temporary = row2[cursorx - 1];
								row2[cursorx - 1] = 0;
								Console.SetCursorPosition(cursorx, 3);
								if (row3[cursorx - 2] == temporary && row3[cursorx] == temporary)
								{
									row3[cursorx - 2] = 0;
									row3[cursorx] = 0;
									cursory++;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("   ");
									Thread.Sleep(100);
									score += 15;
									create_number(3);
								}
								else if (row3[cursorx] == temporary)
								{
									row3[cursorx] = 0;
									cursory++;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else if (row3[cursorx - 2] == temporary)
								{
									row3[cursorx - 2] = 0;
									cursory++;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else
								{
									row3[cursorx - 1] = temporary;
									Console.WriteLine(row3[cursorx - 1]);
									cursory++;
								}
							}
						}
					}

					// W key
					else if (cki.KeyChar == 119)
					{
						if (cursory == 2 && row2[cursorx - 1] != 0 && row2[cursorx - 2] == 0 && row2[cursorx] == 0)
						{
							if (row1[cursorx - 1] != 0)
							{
								continue;
							}
							else if (row1[cursorx - 1] == 0)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(" ");
								temporary = row2[cursorx - 1];
								row2[cursorx - 1] = 0;
								Console.SetCursorPosition(cursorx, 1);
								if (row1[cursorx - 2] == temporary && row1[cursorx] == temporary)
								{
									row1[cursorx - 2] = 0;
									row1[cursorx] = 0;
									cursory--;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("   ");
									Thread.Sleep(100);
									score += 15;
									create_number(3);
								}
								else if (row1[cursorx] == temporary)
								{
									row1[cursorx] = 0;
									cursory--;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else if (row1[cursorx - 2] == temporary)
								{
									row1[cursorx - 2] = 0;
									cursory--;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else
								{
									row1[cursorx - 1] = temporary;
									Console.WriteLine(row1[cursorx - 1]);
									cursory--;
								}
							}
						}
						else if (cursory == 3 && row3[cursorx - 1] != 0 && row3[cursorx - 2] == 0 && row3[cursorx] == 0)
						{
							if (row2[cursorx - 1] != 0)
							{
								continue;
							}
							else if (row2[cursorx - 1] == 0)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(" ");
								temporary = row3[cursorx - 1];
								row3[cursorx - 1] = 0;
								Console.SetCursorPosition(cursorx, 2);
								if (row2[cursorx - 2] == temporary && row2[cursorx] == temporary)
								{
									row2[cursorx - 2] = 0;
									row2[cursorx] = 0;
									cursory--;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("   ");
									Thread.Sleep(100);
									score += 15;
									create_number(3);
								}
								else if (row2[cursorx] == temporary)
								{
									row2[cursorx] = 0;
									cursory--;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else if (row2[cursorx - 2] == temporary)
								{
									row2[cursorx - 2] = 0;
									cursory--;
									Thread.Sleep(100);
									Console.SetCursorPosition(cursorx - 1, cursory);
									Console.WriteLine("  ");
									Thread.Sleep(100);
									score += 10;
									create_number(2);
								}
								else
								{
									row2[cursorx - 1] = temporary;
									Console.WriteLine(row2[cursorx - 1]);
									cursory--;
								}
							}
						}
					}

					// A key
					else if (cki.KeyChar == 97)
					{
						if (cursory == 1 && row1[cursorx - 1] != 0 && row1[cursorx - 2] == 0 && row1[cursorx] == 0)
						{
							Console.SetCursorPosition(cursorx, cursory);
							Console.WriteLine(" ");
							temporary = row1[cursorx - 1];
							row1[cursorx - 1] = 0;
							while ((cursorx - 2) > 0 && row1[cursorx - 2] == 0)
							{
								cursorx--;
							}
							if ((cursorx - 2) > 0 && row1[cursorx - 2] == temporary)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(temporary);
								row1[cursorx - 2] = 0;
								cursorx--;
								Thread.Sleep(100);
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine("  ");
								Thread.Sleep(100);
								score += 10;
								create_number(2);
							}
							else
							{
								row1[cursorx - 1] = temporary;
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(row1[cursorx - 1]);
							}
						}

						else if (cursory == 2 && row2[cursorx - 1] != 0 && row2[cursorx - 2] == 0 && row2[cursorx] == 0)
						{
							Console.SetCursorPosition(cursorx, cursory);
							Console.WriteLine(" ");
							temporary = row2[cursorx - 1];
							row2[cursorx - 1] = 0;
							while ((cursorx - 2) > 0 && row2[cursorx - 2] == 0)
							{
								cursorx--;
							}
							if ((cursorx - 2) > 0 && row2[cursorx - 2] == temporary)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(temporary);
								row2[cursorx - 2] = 0;
								cursorx--;
								Thread.Sleep(100);
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine("  ");
								Thread.Sleep(100);
								score += 10;
								create_number(2);
							}
							else
							{
								row2[cursorx - 1] = temporary;
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(row2[cursorx - 1]);
							}
						}

						else if (cursory == 3 && row3[cursorx - 1] != 0 && row3[cursorx - 2] == 0 && row3[cursorx] == 0)
						{
							Console.SetCursorPosition(cursorx, cursory);
							Console.WriteLine(" ");
							temporary = row3[cursorx - 1];
							row3[cursorx - 1] = 0;
							while ((cursorx - 2) > 0 && row3[cursorx - 2] == 0)
							{
								cursorx--;
							}
							if ((cursorx - 2) > 0 && row3[cursorx - 2] == temporary)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(temporary);
								row3[cursorx - 2] = 0;
								cursorx--;
								Thread.Sleep(100);
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine("  ");
								Thread.Sleep(100);
								score += 10;
								create_number(2);
							}
							else
							{
								row3[cursorx - 1] = temporary;
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(row3[cursorx - 1]);
							}
						}
					}

					// D key
					else if (cki.KeyChar == 100)
					{
						if (cursory == 1 && row1[cursorx - 1] != 0 && row1[cursorx - 2] == 0 && row1[cursorx] == 0)
						{
							Console.SetCursorPosition(cursorx, cursory);
							Console.WriteLine(" ");
							temporary = row1[cursorx - 1];
							row1[cursorx - 1] = 0;
							while (cursorx < 31 && row1[cursorx] == 0)
							{
								cursorx++;
							}
							if ((cursorx) < 31 && row1[cursorx] == temporary)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(temporary);
								row1[cursorx] = 0;
								Thread.Sleep(100);
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine("  ");
								Thread.Sleep(100);
								score += 10;
								create_number(2);
							}
							else
							{
								row1[cursorx - 1] = temporary;
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(row1[cursorx - 1]);
							}
						}

						else if (cursory == 2 && row2[cursorx - 1] != 0 && row2[cursorx - 2] == 0 && row2[cursorx] == 0)
						{
							Console.SetCursorPosition(cursorx, cursory);
							Console.WriteLine(" ");
							temporary = row2[cursorx - 1];
							row2[cursorx - 1] = 0;
							while (cursorx < 31 && row2[cursorx] == 0)
							{
								cursorx++;
							}
							if ((cursorx) < 31 && row2[cursorx] == temporary)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(temporary);
								row2[cursorx] = 0;
								Thread.Sleep(100);
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine("  ");
								Thread.Sleep(100);
								score += 10;
								create_number(2);
							}
							else
							{
								row2[cursorx - 1] = temporary;
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(row2[cursorx - 1]);
							}
						}

						else if (cursory == 3 && row3[cursorx - 1] != 0 && row3[cursorx - 2] == 0 && row3[cursorx] == 0)
						{
							Console.SetCursorPosition(cursorx, cursory);
							Console.WriteLine(" ");
							temporary = row3[cursorx - 1];
							row3[cursorx - 1] = 0;
							while (cursorx < 31 && row3[cursorx] == 0)
							{
								cursorx++;
							}
							if ((cursorx) < 31 && row3[cursorx] == temporary)
							{
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(temporary);
								row3[cursorx] = 0;
								Thread.Sleep(100);
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine("  ");
								Thread.Sleep(100);
								score += 10;
								create_number(2);
							}
							else
							{
								row3[cursorx - 1] = temporary;
								Console.SetCursorPosition(cursorx, cursory);
								Console.WriteLine(row3[cursorx - 1]);
							}
						}
					}

					// escape for the quit
					else if (cki.Key == ConsoleKey.Escape)
					{
						Console.SetCursorPosition(0, 8);
						break;
					}
				}

				if (game_over()) 
				{
					Console.SetCursorPosition(41, 3);
					Console.WriteLine("Game Over!");
					Console.SetCursorPosition(0, 8);
					break;
				}
				Console.SetCursorPosition(cursorx, cursory);
			}
		}
	}
}