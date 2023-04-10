# -*- coding: utf-8 -*-
"""
Created on Thu Dec 20 17:09:46 2021

@author: Gürkan Bıyık 2020510019
"""

stop_words = ['able', 'about', 'above', 'abroad', 'according', 'accordingly',
        'across', 'actually', 'adj', 'after', 'afterwards', 'again', 'against',
        'ago', 'ahead', 'aint', 'all', 'allow', 'allows', 'almost', 'alone',
        'along', 'alongside', 'already', 'also', 'although', 'always', 'am',
        'amid', 'amidst', 'among', 'amongst', 'an', 'and', 'another', 'any',
        'anybody', 'anyhow', 'anyone', 'anything', 'anyway', 'anyways',
        'anywhere', 'apart', 'appear', 'appreciate', 'appropriate', 'are',
        'arent', 'around', 'as', 'as', 'aside', 'ask', 'asking', 'associated',
        'at', 'available', 'away', 'awfully', 'back', 'backward', 'backwards',
        'be', 'became', 'because', 'become', 'becomes', 'becoming', 'been',
        'before', 'beforehand', 'begin', 'behind', 'being', 'believe', 'below',
        'beside', 'besides', 'best', 'better', 'between', 'beyond', 'both',
        'brief', 'but', 'by', 'came', 'can', 'cannot', 'cant', 'cant',
        'caption', 'cause', 'causes', 'certain', 'certainly', 'changes',
        'clearly', 'cmon', 'co', 'co', 'com', 'come', 'comes', 'concerning',
        'consequently', 'consider', 'considering', 'contain', 'containing',
        'contains', 'corresponding', 'could', 'couldnt', 'course', 'cs',
        'currently', 'dare', 'darent', 'definitely', 'described', 'despite',
        'did', 'didnt', 'different', 'directly', 'do', 'does', 'doesnt',
        'doing', 'done', 'dont', 'down', 'downwards', 'during', 'each', 'edu',
        'eg', 'eight', 'eighty', 'either', 'else', 'elsewhere', 'end',
        'ending', 'enough', 'entirely', 'especially', 'et', 'etc', 'even',
        'ever', 'evermore', 'every', 'everybody', 'everyone', 'everything',
        'everywhere', 'ex', 'exactly', 'example', 'except', 'fairly', 'far',
        'farther', 'few', 'fewer', 'fifth', 'first', 'five', 'followed',
        'following', 'follows', 'for', 'forever', 'former', 'formerly',
        'forth', 'forward', 'found', 'four', 'from', 'further', 'furthermore',
        'get', 'gets', 'getting', 'given', 'gives', 'go', 'goes', 'going',
        'gone', 'got', 'gotten', 'greetings', 'had', 'hadnt', 'half',
        'happens', 'hardly', 'has', 'hasnt', 'have', 'havent', 'having', 'he',
        'hed', 'hell', 'hello', 'help', 'hence', 'her', 'here', 'hereafter',
        'hereby', 'herein', 'heres', 'hereupon', 'hers', 'herself', 'hes',
        'hi', 'him', 'himself', 'his', 'hither', 'hopefully', 'how', 'howbeit',
        'however', 'hundred', 'id', 'ie', 'if', 'ignored', 'ill', 'im',
        'immediate', 'in', 'inasmuch', 'inc', 'inc', 'indeed', 'indicate',
        'indicated', 'indicates', 'inner', 'inside', 'insofar', 'instead',
        'into', 'inward', 'is', 'isnt', 'it', 'itd', 'itll', 'its', 'its',
        'itself', 'ive', 'just', 'k', 'keep', 'keeps', 'kept', 'know', 'known',
        'knows', 'last', 'lately', 'later', 'latter', 'latterly', 'least',
        'less', 'lest', 'let', 'lets', 'like', 'liked', 'likely', 'likewise',
        'little', 'look', 'looking', 'looks', 'low', 'lower', 'ltd', 'made',
        'mainly', 'make', 'makes', 'many', 'may', 'maybe', 'maynt', 'me',
        'mean', 'meantime', 'meanwhile', 'merely', 'might', 'mightnt', 'mine',
        'minus', 'miss', 'more', 'moreover', 'most', 'mostly', 'mr', 'mrs',
        'much', 'must', 'mustnt', 'my', 'myself', 'name', 'namely', 'nd',
        'near', 'nearly', 'necessary', 'need', 'neednt', 'needs', 'neither',
        'never', 'neverf', 'neverless', 'nevertheless', 'new', 'next', 'nine',
        'ninety', 'no', 'nobody', 'non', 'none', 'nonetheless', 'noone',
        'noone', 'nor', 'normally', 'not', 'nothing', 'notwithstanding',
        'novel', 'now', 'nowhere', 'obviously', 'of', 'off', 'often', 'oh',
        'ok', 'okay', 'old', 'on', 'once', 'one', 'ones', 'ones', 'only',
        'onto', 'opposite', 'or', 'other', 'others', 'otherwise', 'ought',
        'oughtnt', 'our', 'ours', 'ourselves', 'out', 'outside', 'over',
        'overall', 'own', 'particular', 'particularly', 'past', 'per',
        'perhaps', 'placed', 'please', 'plus', 'possible', 'presumably',
        'probably', 'provided', 'provides', 'que', 'quite', 'qv', 'rather',
        'rd', 're', 'really', 'reasonably', 'recent', 'recently', 'regarding',
        'regardless', 'regards', 'relatively', 'respectively', 'right',
        'round', 'said', 'same', 'saw', 'say', 'saying', 'says', 'second',
        'secondly', 'see', 'seeing', 'seem', 'seemed', 'seeming', 'seems',
        'seen', 'self', 'selves', 'sensible', 'sent', 'serious', 'seriously',
        'seven', 'several', 'shall', 'shant', 'she', 'shed', 'shell', 'shes',
        'should', 'shouldnt', 'since', 'six', 'so', 'some', 'somebody', 'someday',
        'somehow', 'someone', 'something', 'sometime', 'sometimes', 'somewhat',
        'somewhere', 'soon', 'sorry', 'specified', 'specify', 'specifying',
        'still', 'sub', 'such', 'sup', 'sure', 'take', 'taken', 'taking',
        'tell', 'tends', 'th', 'than', 'thank', 'thanks', 'thanx', 'that',
        'thatll', 'thats', 'thats', 'thatve', 'the', 'their', 'theirs', 'them',
        'themselves', 'then', 'thence', 'there', 'thereafter', 'thereby',
        'thered', 'therefore', 'therein', 'therell', 'therere', 'theres',
        'theres', 'thereupon', 'thereve', 'these', 'they', 'theyd', 'theyll',
        'theyre', 'theyve', 'thing', 'things', 'think', 'third', 'thirty',
        'this', 'thorough', 'thoroughly', 'those', 'though', 'three',
        'through', 'throughout', 'thru', 'thus', 'till', 'to', 'together',
        'too', 'took', 'toward', 'towards', 'tried', 'tries', 'truly', 'try',
        'trying', 'ts', 'twice', 'two', 'un', 'under', 'underneath', 'undoing',
        'unfortunately', 'unless', 'unlike', 'unlikely', 'until', 'unto', 'up',
        'upon', 'upwards', 'us', 'use', 'used', 'useful', 'uses', 'using',
        'usually', 'v', 'value', 'various', 'versus', 'very', 'via', 'viz',
        'vs', 'want', 'wants', 'was', 'wasnt', 'way', 'we', 'wed', 'welcome',
        'well', 'well', 'went', 'were', 'were', 'werent', 'weve', 'what',
        'whatever', 'whatll', 'whats', 'whatve', 'when', 'whence', 'whenever',
        'where', 'whereafter', 'whereas', 'whereby', 'wherein', 'wheres',
        'whereupon', 'wherever', 'whether', 'which', 'whichever', 'while',
        'whilst', 'whither', 'who', 'whod', 'whoever', 'whole', 'wholl',
        'whom', 'whomever', 'whos', 'whose', 'why', 'will', 'willing', 'wish',
        'with', 'within', 'without', 'wonder', 'wont', 'would', 'wouldnt',
        'yes', 'yet', 'you', 'youd', 'youll', 'your', 'youre', 'yours',
        'yourself', 'yourselves', 'youve', 'zero', 'a', 'hows', 'i', 'whens',
        'whys', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'uucp', 'w', 'x', 'y', 'z', 'i', 'www',
        'amount', 'bill', 'bottom', 'call', 'computer', 'con', 'couldnt',
        'cry', 'de', 'describe', 'detail', 'due', 'eleven', 'empty', 'fifteen',
        'fifty', 'fill', 'find', 'fire', 'forty', 'front', 'full', 'give',
        'hasnt', 'herse', 'himse', 'interest', 'itse', 'mill', 'move', 'myse',
        'part', 'put', 'show', 'side', 'sincere', 'sixty', 'system', 'ten',
        'thick', 'thin', 'top', 'twelve', 'twenty', 'abst', 'accordance',
        'act', 'added', 'adopted', 'affected', 'affecting', 'affects', 'ah',
        'announce', 'anymore', 'apparently', 'approximately', 'aren', 'arent',
        'arise', 'auth', 'beginning', 'beginnings', 'begins', 'biol',
        'briefly', 'ca', 'date', 'ed', 'effect', 'etal', 'ff', 'fix', 'gave',
        'giving', 'heres', 'hes', 'hid', 'home', 'id', 'im', 'immediately',
        'importance', 'important', 'index', 'information', 'invention', 'itd',
        'keys', 'kg', 'km', 'largely', 'lets', 'line', 'll', 'means', 'mg',
        'million', 'ml', 'mug', 'na', 'nay', 'necessarily', 'nos', 'noted',
        'obtain', 'obtained', 'omitted', 'ord', 'owing', 'page', 'pages',
        'poorly', 'possibly', 'potentially', 'pp', 'predominantly', 'present',
        'previously', 'primarily', 'promptly', 'proud', 'quickly', 'ran',
        'readily', 'ref', 'refs', 'related', 'research', 'resulted',
        'resulting', 'results', 'run', 'sec', 'section', 'shed', 'shes',
        'showed', 'shown', 'showns', 'shows', 'significant', 'significantly',
        'similar', 'similarly', 'slightly', 'somethan', 'specifically',
        'state', 'states', 'stop', 'strongly', 'substantially', 'successfully',
        'sufficiently', 'suggest', 'thered', 'thereof', 'therere', 'thereto',
        'theyd', 'theyre', 'thou', 'thoughh', 'thousand', 'throug', 'til',
        'tip', 'ts', 'ups', 'usefully', 'usefulness', 've', 'vol', 'vols',
        'wed', 'whats', 'wheres', 'whim', 'whod', 'whos', 'widely', 'words',
        'world', 'youd', 'youre']

# word frequency function for one book
def Word_Order_Frequency_One_Book (Book, Word_Order, File_Output):
    
    # when Word_order is not 1 or 2, it returns the error message below
    if Word_Order != 1 and Word_Order != 2:
        print("Second argument can be only 1 or 2.")
        return

    # when File_Output is not entered in correct form, it returns the error message below
    if File_Output.endswith(".txt") == False:
        print("Third argument must contains '.txt' at the end.")
        return
    
    try:
        # reading the Book and assigning it a string variable book to use
        file = open(Book,'r',encoding="utf-8")
        book = str(file.read())

        # closing the file
        file.close()

        # editing the book and removing all '\n' from the variable book
        book = book.lower()
        book = book.replace("\n", " ")

        # determining the punctuations and also digits in the variable book
        punctuations = set()
        for i in book:
            if not i.isalpha():
                punctuations.add(i)

        # removing them from the variable book
        for a in punctuations:
            book = book.replace(a, " ")

        # splitting the words in the variable book
        book_words = list(book.split(" "))

        # deleting the '' (empty pieces of the list book_words) in the variable book
        clear_book_words = list()
        for i in book_words:
            if i != "":
                clear_book_words.append(i)

        # dictionary variable for counting words before ordering
        counted_words = dict()

        # function for one word
        if Word_Order == 1:

            # take each word in clear_book_words
            for word in clear_book_words:

                # if it is not stop word, contniue
                if word not in stop_words:

                    # if word added to the counted_words dictionary, increase by 1 its value
                    if word in counted_words.keys():
                        counted_words[word] += 1

                    # if counted_words dictionary not includes the word, add the word into dictionary
                    else:
                        counted_words[word] = 1

        # function for two words
        elif Word_Order == 2:

            # reading words in two pieces
            for index in range(0,len(clear_book_words)-1):
                word_1 = str(clear_book_words[index])
                word_2 = str(clear_book_words[index+1])

                # if word_1 or word_2 is not stop word, continue
                if not (word_1 in stop_words or word_2 in stop_words):

                    # assiging word_1 and word_2 to a one variable
                    word_1_2 = "{} {}".format(word_1, word_2)

                    # if word_1_2 has been counted, increase by 1 its value 
                    if word_1_2 in counted_words.keys():
                        counted_words[word_1_2] += 1
                    
                    # if word_1_2 not in counted_words dictionary, add word_1_2 into the dictionary
                    else:
                        counted_words[word_1_2] = 1

        # ordering counted_words items by their values descending
        sorted_words = sorted(counted_words.items(), key=lambda x: x[1], reverse=True)

        # creating a file to write
        outfile = open(File_Output,'w',encoding="utf-8")

        # writing headers to the file
        outfile.write(" | WORD      | WORD     |\n")
        outfile.write(" | ORDER     | ORDER    |\n")
        outfile.write(" | FREQUENCY | SEQUENCE |\n")
        outfile.write(" |-----------|----------|\n")

        # writing keys and values alignerly to the file with using format method
        for x in sorted_words:
            outfile.write(" |{: <11}|{}\n".format(x[1], x[0]))

        # closing the file
        outfile.close()

    # except block for syntaxerror
    except SyntaxError:
        print("Please, make sure you write the function correctly.")

    # except block for typeerror
    except TypeError:
        print("Function works with 3 arguments.")

    # except block for filenotfounderror
    except FileNotFoundError:
        print("File or directory is wrong.")

    # except block for any other error
    except:
        print("Unknown Error.")
            
# word frequency function for two books
def Word_Order_Frequency_Two_Books (Book_1, Book_2, Word_Order, File_Output):
    
    # when entered Book_1 and Book_2 same, it returns the message below 
    if Book_1 == Book_2:
        print("Please enter different books.")
        return

    # when Word_order is not 1 or 2, it returns the error message below   
    if Word_Order != 1 and Word_Order != 2:
        print("Third argument can be only 1 or 2.")
        return

    # when File_Output is not entered in correct form, it returns the error message below    
    if File_Output.endswith(".txt") == False:
        print("Fourth argument must contain '.txt' at the end.")
        return
    
    try:
        # reading the Book_1 and assigning it a string variable book1 to use
        file1 = open(Book_1,'r',encoding="utf-8")
        book1 = str(file1.read())

        # closing the file
        file1.close()

        # editing the book1 and removing all '\n' from the variable book1
        book1 = book1.lower()
        book1 = book1.replace("\n", " ")

        # determining the punctuations and also digits in the variable book1
        punctuations1 = set()
        for i in book1:
            if not i.isalpha():
                punctuations1.add(i)

        # removing them from the variable book1
        for a in punctuations1:
            book1 = book1.replace(a, " ")

        # splitting the words in the variable book1
        book1_words = list(book1.split(" "))

        # deleting the '' (empty pieces of the list book_words) in the variable book
        clear_book1_words = list()
        for i in book1_words:
            if i != "":
                clear_book1_words.append(i)

        # reading the Book_2 and assigning it a string variable book2 to use
        file2 = open(Book_2,'r',encoding="utf-8")
        book2 = str(file2.read())

        # closing the file
        file2.close()

        # editing the book1 and removing all '\n' from the variable book2
        book2 = book2.lower()
        book2 = book2.replace("\n", " ")

        # determining the punctuations and also digits in the variable book2
        punctuations2 = set()
        for i in book2:
            if not i.isalpha():
                punctuations2.add(i)

        # removing them from the variable book2
        for a in punctuations2:
            book2 = book2.replace(a, " ")

        # splitting the words in the variable book1
        book2_words = list(book2.split(" "))

        # deleting the '' (empty pieces of the list book_words) in the variable book
        clear_book2_words = list()
        for i in book2_words:
            if i != "":
                clear_book2_words.append(i)
        
        # dictionary variable for counting words before ordering
        counted_words = dict()

        # function for one word in both books
        if Word_Order == 1:
            
            # creating a word set for book1 with using the clear_book1_words list
            clear_book1_words_set = set(clear_book1_words)

            # creating a word set for book1 with using the clear_book1_words list
            clear_book2_words_set = set(clear_book2_words)

            # creating a set variable for words to call later
            books_word_set = set()

            # adding words which are in clear_book1_words to the books_word_set
            for word in clear_book1_words_set:
                if word not in stop_words:
                    books_word_set.add(word)
            
            # adding words which are in clear_book2_words to the books_word_set   
            for word in clear_book2_words_set:
                if word not in stop_words:
                    books_word_set.add(word) 
            
            # assigning words to the dictionary counted_words before counting (value = [total,book1-frequency,book2-frequency])
            for word in books_word_set:
                counted_words[word] = [0,0,0]
            
            # counting words which are in clear_book1_words
            for word in clear_book1_words:
                if word in books_word_set:
                    counted_words[word][0] += 1 # total frequency
                    counted_words[word][1] += 1 # book_1 frequency

            # counting words which are in clear_book2_words        
            for word in clear_book2_words:
                if word in books_word_set:
                    counted_words[word][0] += 1 # total frequency
                    counted_words[word][2] += 1 # book_2 frequency

        # function for two word in both books
        elif Word_Order == 2:
           
            # determining double words in clear_book1_words which are not include stop word.
            # then appending them into clear_book1_word_patterns
            clear_book1_word_patterns = list()
            for index in range(0,len(clear_book1_words)-1):
                word_1 = str(clear_book1_words[index])
                word_2 = str(clear_book1_words[index+1])
                if not (word_1 in stop_words or word_2 in stop_words):
                    word_1_2 = "{} {}".format(word_1, word_2)
                    clear_book1_word_patterns.append(word_1_2)

            # determining double words in clear_book2_words which are not include stop word.
            # then appending them into clear_book2_word_patterns
            clear_book2_word_patterns = list()
            for index in range(0,len(clear_book2_words)-1):
                word_1 = str(clear_book2_words[index])
                word_2 = str(clear_book2_words[index+1])
                if not (word_1 in stop_words or word_2 in stop_words):
                    word_1_2 = "{} {}".format(word_1, word_2)
                    clear_book2_word_patterns.append(word_1_2)

            # creating a set variable for double words to call later
            books_word_patterns_set = set()

            # adding double words which are in clear_book1_word_patterns list to the books_word_patterns_set
            for word_pattern in clear_book1_word_patterns:
                books_word_patterns_set.add(word_pattern)

            # adding double words which are in clear_book1_word_patterns list to the books_word_patterns_set
            for word_pattern in clear_book2_word_patterns:
                books_word_patterns_set.add(word_pattern)
            
            # assigning double words to the dictionary counted_words before counting (value = [total,book1-frequency,book2-frequency])
            for word_pattern in books_word_patterns_set:
                counted_words[word_pattern] = [0,0,0]

            # counting double words in the clear_book1_word_patterns list
            # then increasing their values by one
            for word_pattern in clear_book1_word_patterns:
                if word_pattern in counted_words.keys():
                    counted_words[word_pattern][0] += 1 # total frequency
                    counted_words[word_pattern][1] += 1 # book_1 frequency

            # counting double words in the clear_book2_word_patterns
            # then increasing their values by one      
            for word_pattern in clear_book2_word_patterns:
                if word_pattern in counted_words.keys():
                    counted_words[word_pattern][0] += 1 # total frequency
                    counted_words[word_pattern][2] += 1 # book_1 frequency
                    
        # ordering items by their values descending
        sorted_words = sorted(counted_words.items(), key=lambda x: x[1], reverse=True)

        # creating a file to write
        outfile = open(File_Output,'w',encoding="utf-8")

        # writing headers to the file
        outfile.write(" | TOTAL     | BOOK 1    | BOOK 2    | WORD     |\n")
        outfile.write(" | ORDER     | ORDER     | ORDER     | ORDER    |\n")
        outfile.write(" | FREQUENCY | FREQUENCY | FREQUENCY | SEQUENCE |\n")
        outfile.write(" |-----------|-----------|-----------|----------|\n")

        # writing keys and values alignerly to the file with using format method
        for x in sorted_words:
            outfile.write(" |{: <11}|{:<11}|{:<11}|{}\n".format(x[1][0], x[1][1], x[1][2], x[0]))

        # closing the file
        outfile.close()

    # except block for syntaxerror
    except SyntaxError:
        print("Please, make sure you write the function correctly.")

    # except block for typeerror
    except TypeError:
        print("Function works with 4 arguments.")

    # except block for filenotfounderror
    except FileNotFoundError:
        print("File or directory is wrong.")

    # except block for any other error
    except:
        print("Unknown Error.")