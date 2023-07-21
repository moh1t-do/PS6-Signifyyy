public class DrivingScoring {
    public void speed(){

    }
    public void speedScoring(long totalTime, long highSpeedTime, long preScore){
        double percentage = (highSpeedTime/totalTime) * 100;
        //Driving scoring model intake from SQLite database system for speed only
        if(percentage >= 10 && percentage <= 40)
        {

            //Deduct -1 from speed score system
            //Aggressive count++
        }
        else if(percentage >= 40)
        {
            //Deduct -2 from speed score system
            //Risky count++
        }
        else if(percentage <= 10)
        {
            //Add +3 from to speed score system
        }
        else
        {
            //Add +2 from to speed score system
        }
    }
    public void accScoring(double acceleration, long highSpeedTime, long preScore){
        double percentage = (highSpeedTime/totalTime) * 100;
        //Driving scoring model intake from SQLite database system for speed only
        if(percentage >= 10 && percentage <= 40)
        {

            //Deduct -1 from speed score system
            //Aggressive count++
        }
        else if(percentage >= 40)
        {
            //Deduct -2 from speed score system
            //Risky count++
        }
        else if(percentage <= 10)
        {
            //Add +3 from to speed score system
        }
        else
        {
            //Add +2 from to speed score system
        }
    }
}
