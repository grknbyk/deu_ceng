"""
analyze_data is takes data set as string list as parameter,
then parse them into float type,
then sorts them in ascending order then prints in order of
n: numbers of data in the data set
mod: mod or mods of the data set
min: minimum value of the data set
max: maximum value of the data set
mean: average of the data set
variance: variance of the data set
sd: standard deviation of the data set
lq: lower quartile of the data set
median: median of the data set
uq: upper quartile of the data set
iqr: uq-lq
lower inner fence: lq-(3/2)*iqr 
upper inner fence: uq+(3/2)*iqr 
lower outer fence: lq-3*iqr 
upper outer fence: uq+3*iqr
outliers: print the values that lower than lower inner fence or higher than upper inner fence from the data
suspects outliers: print the values that higher than lower outer fence or lower than upper outer fence from the outliers
highly suspects outliers: print the values that lower than lower outer fence or higher than upper outer fence from the outliers
print the sorted form of the data
"""
def analyze_data(data_set):
    # Parse strings into floats
    data = [float(x) for x in data_set]
    # Sort data in ascending order
    data.sort()
    # Number of data points
    n = len(data)
    # Mod
    mod = max(set(data), key = data.count)
    # Minimum value
    minimum = min(data)
    # Maximum value
    maximum = max(data)
    # Mean
    mean = sum(data)/n
    # Variance
    variance = sum([((x - mean) ** 2) for x in data]) / n
    # Standard deviation
    sd = variance**(1/2)
    # Quartiles
    q1 = data[int((n+1)/4)-1]
    median = data[int(n/2)-1]
    q3 = data[int((3*(n+1))/4)-1]
    # IQR
    iqr = q3 - q1
    # Inner fences
    lower_inner_fence = q1 - (3/2) * iqr
    upper_inner_fence = q3 + (3/2) * iqr
    # Outer fences
    lower_outer_fence = q1 - 3 * iqr
    upper_outer_fence = q3 + 3 * iqr
    # Outliers
    outliers = [x for x in data if x < lower_inner_fence or x > upper_inner_fence]
    # Suspected outliers
    suspects_outliers = [x for x in outliers if x > lower_outer_fence and x < upper_outer_fence]
    # Highly suspected outliers
    highly_suspects_outliers = [x for x in outliers if x < lower_outer_fence or x > upper_outer_fence]
    # Print results
    print("n: ", n)
    print("mod: ", mod)
    print("range:", maximum-minimum)
    print("min: ", minimum)
    print("max: ", maximum)
    print("mean: ", mean)
    print("variance: ", variance)
    print("sd: ", sd)
    print("lq: ", q1)
    print("median: ", median)
    print("uq: ", q3)
    print("iqr: ", iqr)
    print("lower inner fence: ", lower_inner_fence)
    print("upper inner fence: ", upper_inner_fence)
    print("lower outer fence: ", lower_outer_fence)
    print("upper outer fence: ", upper_outer_fence)
    print("outliers: ", *outliers)
    print("suspects outliers: ", *suspects_outliers)
    print("highly suspects outliers: ", *highly_suspects_outliers)
    print("sorted data: ", *data)

text= "0 3 5 5 6 7 7 8 8 8 8 8 9 9 9 9 9 9 10 10 10 11 11 11 11 12 13 13 13 13 13 13 13 13 14 14 14 14 14 14 14 14 14 14 14 15 15 15 16 16 16 16 16 16 16 16 16 16 16 16 16 17 17 17 17 17 17 17 17 17 18 18 19 19 19 19 19 19 19 20 20 20 20 20 20 20 20 20 20 21 21 22 22 23 24 25 26 26 27 27 27 27 29"

splitText = " "

analyze_data(text.split(splitText))