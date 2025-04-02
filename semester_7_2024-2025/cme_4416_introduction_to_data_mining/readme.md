# Rainfall Prediction Project

This project provides a machine learning solution for predicting rainfall for next day based on Australia using historical weather data.

## Dataset
The project uses the "Rain in Australia" dataset from Kaggle:
- **Source**: [Weather Dataset (Rattle Package)](https://www.kaggle.com/datasets/jsphyg/weather-dataset-rattle-package/data)
- **Description**: This dataset contains daily weather observations from numerous Australian weather stations and includes features such as temperature, humidity, pressure, wind speed, and rainfall.
- **File**: `weatherAUS.csv`

## Project Structure

### Files
- `templates/index.html`: HTML template for the web application interface
- `analysis_and_model_devlopment.ipynb`: Jupyter Notebook containing data analysis, model training, evaluation, and visualization
- `app.py`: Flask web application that serves the trained model and provides predictions
- `results.csv`: CSV file containing performance metrics for all tested models
- `weatherAUS.csv`: Original dataset containing Australian weather data
- `random_forest_model_dict.pkl`: Serialized file containing the trained Random Forest model, scaler, input columns, and example input (created during notebook execution)

## Usage

### Model Development
1. Run the Jupyter Notebook to analyze data and train the model:
   ```
   jupyter notebook analysis_and_model_devlopment.ipynb
   ```
   
2. Execute all cells in the notebook by clicking "Run All" or by pressing Shift+Enter through each cell
   - Upon successful execution, the notebook will create `random_forest_model_dict.pkl`

### Running the Web Application
1. Start the Flask application:
   ```
   python app.py
   ```

2. Open your web browser and navigate to `http://localhost:5000` (or the URL displayed in the terminal)

3. Input weather parameters through the web interface to get rainfall predictions for the next day