# drive-and-care
Problem-Statement-6
Overall workflow of our application: 
1) User Registration:  When a user visits the application for the first time, he/she is required to register with an appropriate username, password and details like age, city and daily activities. User’s consent is taken for accessing their health and driving details. From the next time, user can sign-in with the created username and password/ automatically.
   
2) Data Collection:  User’s data is collected from accelerometer, gyroscope , GPS , Google Health API and Google Maps. This data is further categorized into two types – one for gamifying fitness and the other one for gamifying driving behavior.
 
3) Driving Scoring Model:  Data collected from accelerometer, gyroscope and GPS is used in an algorithm to compute metrics like speed , acceleration, cornering speed and braking intensity. The scoring model subdivides this data into 
       5 driving behaviours:
       i) Aggressive Driving           ii) Risky Driving           iii) Normal Driving         iv) Efficient Driving        v) Under Performant Driving
       Finally , mean of all the metrics-based scores is taken to compute the overall driving score and overall driving behaviour.
   
4) Fitness Scoring Model:  Data collected from GPS , Google Health API and Google Maps is shared with the registered hospitals and fitness centres who are in collaboration with our application. This data is fed into an algorithm to compute health-activities based metrics:
      i) Total Steps                                ii) Distance Covered                                  iii) Calories Burnt 
      and internal health based metrics:
      i) Sleep Duration                         ii) Oxygen Saturation(SpO2)                    iii) Heart Rate
      Finally , mean of all the metrics-based scores is taken to compute the overall fitness score.
   
5) Incentivization System:  Based on the overall driving and fitness scores , users will be given discounts on vehicle insurances   , health insurances and Bajaj products. Certain customers with good records would also be given free trial of Bajaj products for a certain time duration.

## Collaborators 🤖
| Name      | GitHub Profile     |
| :------------- | :----------: |
|  D Anuj Kumar  | [GitHub](https://github.com/danujkumar) |
|  Prerna Singh | [GitHub]( https://github.com/prernasngh) |
|  Harsh Dewangan  | [GitHub](https://github.com/harshdew02) |
|  Bhupendra Kumar Gehlot | [GitHub](https://github.com/Bhupendra-Gehlot1) |
|  Mohit Doraiburu  | [GitHub](https://github.com/moh1t-do) |
