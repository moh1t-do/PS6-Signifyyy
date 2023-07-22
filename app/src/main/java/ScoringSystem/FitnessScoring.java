package ScoringSystem;

public class FitnessScoring {
    long totalScore = 0;
    long hrewards = 0;
    public void totalFitnessScore(long steps, long calories, long sleep, long pulse, int age){

        if (steps > 100){
            totalScore += steps/100;
        }

        if (calories > 100){
            totalScore += calories/25;
        }

        if (sleep > 1 && sleep < 8){
            totalScore += sleep*25;
        }
//        if (pulse)

    }

    public void rewardSystem(int age, int steps, int calories){
        long rSteps;
        float  rCal;

        if (age >= 5 && age <= 18){
            rSteps = 12000;
            rCal = 3;

        } else if (age >= 19 && age <= 30) {
            rSteps = 10000;
            rCal = 2.5f;
        }
        else if (age >= 31 && age <= 50) {
            rSteps = 8000;
            rCal = 2;
        }
        else {
            rSteps = 7000;
            rCal = 1.5f;
        }

        if (steps > rSteps){
            totalScore += 500;
        }

        if (calories > rCal){
            totalScore += 500;
        }

        if (totalScore > 10000){
            totalScore -= 10000;
            hrewards++;
        }
    }
}
