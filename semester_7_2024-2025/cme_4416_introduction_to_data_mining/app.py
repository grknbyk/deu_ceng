# app.py
from flask import Flask, render_template, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

# Load model components
model_data = joblib.load("random_forest_model_dict.pkl")
model = model_data["model"]
scaler = model_data["scaler"]
features = model_data["selected_features"]

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/predict", methods=["POST"])
def predict():
    try:
        # Get form data
        input_data = {
            "Humidity3pm": float(request.form["humidity3pm"]),
            "Humidity9am": float(request.form["humidity9am"]),
            "Pressure3pm": float(request.form["pressure3pm"]),
            "Pressure9am": float(request.form["pressure9am"]),
            "Temp3pm": float(request.form["temp3pm"]),
            "Temp9am": float(request.form["temp9am"]),
        }

        # Create input array in feature order
        input_array = np.array([[input_data[feature] for feature in features]])
        
        # Scale input
        scaled_input = scaler.transform(input_array)
        
        # Make prediction
        prediction = model.predict(scaled_input)
        probability = model.predict_proba(scaled_input)[0]
        
        # Format response
        result = {
            "prediction": "Rain" if prediction[0] == 1 else "No Rain",
            "probability": round(max(probability) * 100, 2)
        }
        
        return jsonify(result)
        
    except Exception as e:
        return jsonify({"error": str(e)})

if __name__ == "__main__":
    app.run(debug=True)