"""
group 18
2020510019 Gürkan Bıyık
2020510083 Abdulkadir Öksüz
"""

import re
import csv
import json

# dictionary for operators to access them by string
OPERATORS = {
"=" : lambda x,y: x == y,
"!=": lambda x,y: x != y,
"<" : lambda x,y: x <  y,
">" : lambda x,y: x >  y,
">=": lambda x,y: x >= y,
"<=": lambda x,y: x <= y,
"!<": lambda x,y: x >=  y,
"!>": lambda x,y: x <=  y,
}

# patterns to validate and get information
INSERT_PATTERN    = r"(?i)^INSERT\s+INTO\s+([^\s]+)\s+VALUES\s*\((.+)\)\s*$"
DELETE_PATTERN    = r"(?i)^DELETE\s+FROM\s+([^\s]+)\s+WHERE\s+(.+)\s*$"
SELECT_PATTERN    = r"(?i)^SELECT\s+(.+)\s+FROM\s+([^\s]+)\s+WHERE\s+(.+?)\s+(ORDER\s+BY\s+(ASC|DSC))\s*$"
CONDITION_PATTERN = r"(?i)([^\s]+)\s*(=|!=|<=|>=|<|>|!<|!>)\s*(.+)"
AND_PATTERN       = r"(?i)(.+)\s+AND\s+(.+)"
OR_PATTERN        = r"(?i)(.+)\s+OR\s+(.+)"
STR_PATTERN       = r"(?i)^(\"|\')(.*?)(\1)$"

# display function for printing data in table format for select query
def display(lengths, headers, records):
    # Print headers with left alignment
    header_line = " | ".join(f"{header:<{lengths[i]}}" for i, header in enumerate(headers))
    print(header_line)

    # Print line after headers
    line = "-+-".join('-' * length for length in lengths)
    print(line)

    # Print records with left alignment
    for record in records:
        record_line = " | ".join(f"{field:<{lengths[i]}}" for i, field in enumerate(record))
        print(record_line)
    
# take input from user until it is valid
def input_csv_file():
    while True: # loop until valid input
        input_file = input("Enter csv file name: ") # take input from user
        if input_file.endswith(".csv"): # check if file name ends with .csv
            try: # if then try to open file
                with open(input_file, "r") as f: # open file to read
                    return input_file # return file name if file opened successfully
            except: # if file not found
                print("File not found!") # print error message
        else: # if file name is invalid
            print("Invalid file name!") # print error message

# return dictionary of fields and their indexes to access them by string
def getFields(csv_file, delimtr=","):
    if not csv_file.endswith(".csv"):
        raise Exception("Invalid file name!")
    with open(csv_file, "r") as f:
        reader = csv.reader(f, delimiter=delimtr)
        fields = reader.__next__() # get header
        return {fields[i]:i for i in range(len(fields))} # return dictionary of fields and their indexes

# reading data from csv file and return it as list sorted by id    
def readData(csv_file, delimtr=","):
    if not csv_file.endswith(".csv"):
        raise Exception("Invalid file name!")
    with open(csv_file, "r") as f:
        reader = csv.reader(f, delimiter=delimtr)
        reader.__next__() # skip header
        data = list(reader) # cast to list
        data = [[int(row[0]), row[1], row[2], row[3], int(row[4])] for row in data] # cast id and age to int
        data.sort(key=lambda x: x[0]) # sort by id
        return data

# writing data to csv file
def writeData(csv_file, header, records, delimtr=","):
    if not csv_file.endswith(".csv"): # check if file name ends with .csv
        raise Exception("Invalid file name!") # print error message if file name is invalid
    if len(header) != len(records[0]): # check if header and records are matching
        raise Exception("Header and records are not matching!") # print error message if header and records are not matching
    
    with open(csv_file, "w", newline="") as f: # open file to write
        writer = csv.writer(f, delimiter=delimtr) # create writer
        writer.writerow(header) # write header
        writer.writerows(records) # write data

# writing data to json file
def writeJson(json_file, dictionary):
    if not json_file.endswith(".json"): # check if file name ends with .json
        raise Exception("Invalid file name!") # print error message if file name is invalid
    with open(json_file, "w") as f: # write data to json file with indent 4
        json.dump(dictionary, f, indent=4)

# return True if row satisfies where condition
def whereCondition(condition, row):
    if validCondition := re.search(OR_PATTERN, condition): # check if condition is OR
        return isConditionTrue(validCondition.group(1), row) or isConditionTrue(validCondition.group(2), row)
    elif validCondition := re.search(AND_PATTERN, condition): # check if condition is AND
        return isConditionTrue(validCondition.group(1), row) and isConditionTrue(validCondition.group(2), row)
    else: # check if condition is single
        return isConditionTrue(condition, row) 

# return True if row satisfies single condition
def isConditionTrue(condition, row): 
    if match := re.search(CONDITION_PATTERN, condition): # check if condition is valid
        column_name = match.group(1) # get column name
        if column_name not in fields.keys(): # check if column name is valid
            raise Exception("Invalid column name") # print error message if column name is invalid
        
        operator_type = match.group(2) # get operator type
        row_value = row[fields[match.group(1)]] # get row value
        condition_value = match.group(3) # get condition value
        
        match = re.search(STR_PATTERN, condition_value) # match string pattern
        if match: # check if match with string pattern
            condition_value = match.group(2) # if it is string then get string
        else: # if it is not string then it is number
            try: # try to convert to int
                condition_value = int(condition_value)
            except: # if it is not valid number then raise exception
                raise Exception("Invalid where condition")
        
        # get operator function from OPERATORS dictionary and compare row value and condition value    
        return OPERATORS[operator_type](row_value, condition_value)
    else:
        raise Exception("Invalid where condition") # print error message if condition is invalid
 
# return True if all elements in array1 and array2 are same type in same order  
def isMatchingTypes(array1, array2):
    return all(isinstance(a, type(b)) for a, b in zip(array1, array2))


def selectStatement(valid_statement):
    if (temp__table_name := valid_statement.group(2)) != table_name: # check if table name is valid
        raise Exception("No such table: " + temp__table_name) # print error message if table name is not matching
    
    if (columns := valid_statement.group(1)) == "ALL": # check if columns is ALL
        columns = fields # if it is ALL then get all fields
    else: # if it is not ALL then get columns
        columns = [column.strip() for column in columns.split(",")] # split columns by comma
        if all([(column_name in fields.keys()) for column_name in columns]): # check if all columns are in fields
            columns = {column_name: fields[column_name] for column_name in columns} # create dictionary for given columns like {column_name: column_index}
        else: # if not all columns are in fields then raise exception
            raise Exception("Invalid column name") # print error message if column name is invalid
        
    headers = [column_name for column_name in columns.keys()] # get headers from columns
    lengths = [0] * len(headers) # create lengths array for aligning data for display
    result = [] # create array to hold resulting records
    
    for row in data: # iterate over data
        if whereCondition(valid_statement.group(3), row): # check if row satisfies where condition
            new_row = [row[column_index] for column_index in columns.values()] # get values according to given columns
            result.append(new_row) # append new row to result
            lengths = [max(length, len(str(field))) for length, field in zip(lengths, new_row)] # update lengths array for aligning data for display

    if (order := valid_statement.group(4)) == None: # check if order is None
        raise Exception("Invalid order syntax") # print error message if order is invalid
    elif order.upper().endswith("ASC"): # check if order is ASC
        result.sort(key= lambda x: x[0]) # sort by first column
    elif order.upper().endswith("DSC"): # check if order is DESC
        result.sort(key= lambda x: x[0], reverse=True) # sort by first column in reverse order

    
    display(lengths, headers, result) # display result
    print(f"\n {len(result)} record(s) found.") # print number of records found
    return result # return resulting records

def insertStatement(valid_statement):
    if (temp__table_name := valid_statement.group(1)) != table_name: # check if table name is valid
        raise Exception("No such table: " + temp__table_name) # print error message if table name is not matching
    
    temp_values = valid_statement.group(2).split(",") # split values by comma
    
    values = [] # create array to hold values
    for x in temp_values: # iterate over values
        match = re.search(STR_PATTERN, x) # match string pattern
        if match: # if it is string 
            x = match.group(2) # then get string
        else: # if not 
            try: # try to convert to int
                x = int(x)
            except: # if failed then raise exception
                raise Exception("Invalid value")
        values.append(x) # append value to values
    
    if len(values) != len(fields): # check if number of values is 5
        raise Exception("Invalid number of values") # if not lenghts are same then raise exception
    elif not isMatchingTypes(values, data[0]): # check if types of values are matching with types of fields
        raise Exception("Invalid value types") # if not matching then raise exception
    else: # if everything is valid
        data.append(values) # append values to data
        data.sort(key = lambda x: x[0]) # sort data by first column
        print("Record inserted successfully.") # print success message
        return values # return inserted record

def deleteStatement(valid_statement):
    if (temp__table_name := valid_statement.group(1)) != table_name: # check if table name is valid
        raise Exception("No such table: " + temp__table_name) # print error message if table name is not matching
    
    where_condition = valid_statement.group(2) # get where condition
    
    # used filtering method to delete records, because it is faster than deleting records in data
    filtered_data = [] # create array to hold filtered data
    deleted_records = [] # create array to hold deleted records
    for row in data: # iterate over data
        if whereCondition(where_condition, row): # check if row satisfies where condition
            deleted_records.append(row) # append row to deleted records
        else: # if row does not satisfy where condition
            filtered_data.append(row) # append row to filtered data
    
    data.clear() # clear data
    data.extend(filtered_data) # extend data with filtered data
    
    print(str(len(deleted_records)) + " records deleted") # print number of deleted records message
    return deleted_records # return deleted records
            

# parse sql statement and do operations according to statement and return resulting records
def parseStatement(sql_statement):
    if validatedSelect := re.search(SELECT_PATTERN, sql_statement): # if match select pattern
        return selectStatement(validatedSelect) # then do select statement
    elif validatedInsert := re.search(INSERT_PATTERN, sql_statement): # if match insert pattern
        return insertStatement(validatedInsert)    # then do insert statement
    elif validatedDelete := re.search(DELETE_PATTERN, sql_statement): # if match delete pattern
        return deleteStatement(validatedDelete) # then do delete statement
    else: # if not match any pattern
        raise Exception("Syntax Error") # then raise exception
    

# main program

# initialize variables
delimiter = ";" # delimiter for csv file
# csv_file = input_csv_file() # get csv file name from user
csv_file = "students.csv"
table_name = csv_file.split(".")[0].upper() # get table name from csv file name
fields = getFields(csv_file, delimiter) # get fields from csv file
data = readData(csv_file, delimiter) # get data from csv file
print("Table " + table_name + " loaded successfully.") # print success message

# getting sql statements from user
result_set = {}
while (input_str := input("Enter SQL statement: ")) != "exit":
    try:
        result_data = parseStatement(input_str)
        result_set[input_str] = result_data        
    except Exception as e:
        print(e)

# finalizing program
# print("Saving changes on csv file...")
# writeData(csv_file, fields.keys(), data, delimiter)
saveDB = {"students": [{i:j for i,j in zip(fields.keys(),row)} for row in data]}
# print("Result of queries dumping to json..")
# writeJson("resultSet.json", result_set)
print("Saving changes on json file...")
writeJson("database.json", saveDB)
print("Exiting...")