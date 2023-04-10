using System;

namespace Word_World
{
    class Program
    {
        static bool word_compare(string str, string pttrn)
        //compare the string and pattern with using a temporary string which created via pattern.
        //At the end, it returns true if the temporary string is the same as the word
        {
            bool flag = false; //bool variable for result of compare
            string temporary = ""; //temporary string for compare

            //necessary fixes on string for the appropriate compare
            pttrn = pttrn.ToLower();
            str = str.ToLower();
            pttrn.Replace("ı", "i");
            str.Replace("ı", "i");

            if (pttrn.Contains("*")) //process for the pattern which contains '*'
            {
                int counter = 0; //counter for number of '*'
                for (int x = 0; x < pttrn.Length; x++) if (pttrn[x] == '*') counter++; //counting the '*'

                string[] temp_subs = pttrn.Replace("*", "/*/").Split("/"); // spliting the pattern. e.g( U*ive*s*y -> {"U","*","ive","*","s","*","y"} --> University)

                //process for the pattern like (*an), (*m), (*us)
                if (counter == 1 && pttrn[0] == '*') 
                {
                    flag = str.EndsWith(pttrn.Substring(1)); 
                }

                //process for the pattern like (a* --> act), (ex* --> expect)
                else if (counter == 1 && pttrn[pttrn.Length - 1] == '*') 
                {
                    flag = str.StartsWith(pttrn.Substring(0, pttrn.Length - 1));
                }

                //process for the pattern like (a*r --> angular), (ex*n --> expectation), (p*n --> python)
                else if (counter == 1 && pttrn[0] != '*' && pttrn[pttrn.Length - 1] != '*') 
                {
                    flag = str.StartsWith(temp_subs[0]) && str.EndsWith(temp_subs[2]);
                }

                //process for the pattern like (*i* --> science), (*mp* --> computer)
                else if (counter == 2 && pttrn[0] == '*' && pttrn[pttrn.Length - 1] == '*')
                {
                    flag = str.Contains(pttrn.Substring(1, pttrn.Length - 2)); 
                }

                //process for the pattern like ( U*ive*s*y -> {"U","*","ive","*","s","*","y"} --> University)
                else
                {
                    for (int i = 0; i < temp_subs.Length; i++)
                    {
                        //if pattern has '*' at the end, writes letters of the word to the temporary starting from the index which is length of the temporary string
                        if (i == (temp_subs.Length - 2) && temp_subs[i] == "*" && temp_subs[i + 1] == "" && temporary.Length <= str.Length)  
                        {
                            temporary += str.Substring(temporary.Length);
                            break;
                        }

                        //write word's letters to the temporary until fond the similarty between word and the next index of temp_subs
                        else if (temp_subs[i] == "*") 
                        {
                            int len = temporary.Length + temp_subs[i + 1].Length;
                            while (len < str.Length && temp_subs[i + 1] != str.Substring(temporary.Length, temp_subs[i + 1].Length))
                            {
                                temporary += str[temporary.Length];
                                len = temporary.Length + temp_subs[i + 1].Length;
                            }
                        }

                        //writes the letters
                        //e.g() ( U*ive*s*y -> {"U","*","ive","*","s","*","y"} --> Writes {"U","ive","s","y"} when its their turn)
                        else
                        {
                            temporary += temp_subs[i];
                        }
                    }
                }
            }

            else if (pttrn.Contains("-") && str.Length == pttrn.Length) //process for the pattern which contains '-'
            {
                for (int i = 0; i < pttrn.Length; i++)
                {
                    switch (pttrn[i])
                    {
                        case '-':
                            temporary += str[i];
                            break;
                        default:
                            temporary += pttrn[i];
                            break;
                    }
                }
            }

            else //process for the pattern which not contains '*' or '-' 
            {
                if (str.Length == pttrn.Length)
                {
                    for (int i = 0; i < pttrn.Length; i++)
                    {
                        temporary += pttrn[i];
                    }
                }
            }

            if (str == temporary) flag = true;
            return flag;
        }
        static void Main(string[] args)
        {
            string text, pattern;
            //taking input for text and pattern
            Console.WriteLine("Enter your text:");
            text = Console.ReadLine();
            Console.WriteLine();
            Console.WriteLine("Enter your pattern:");
            pattern = Console.ReadLine();
            Console.WriteLine();

            //purifying text from '.' and ',' and adding words to the array
            text = text.Replace(",", "").Replace(".", "");
            string[] words = text.Split(" ");

            //Creating a word set to prevent printing the same word more than one time
            string words_set = "/"; 

            for (int i = 0; i < words.Length; i++)
            {
                if (words_set.Contains("/" + words[i].ToLower() + "/") == false && word_compare(words[i], pattern))
                {
                    Console.WriteLine(words[i]); //writes word
                    words_set += words[i].ToLower() + "/"; //adds word into word_set to not write again
                }
            }
            Console.ReadLine();
        }
    }
}