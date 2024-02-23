public class Player{
    private String name;
    private int score;

    public Player(){
        this("",0);
    }
    public Player(String name, int score){
        this.name  = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }
    public int getScore(){
        return score;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void updateScore(int addToScore){
        this.score += addToScore;
    }
    public String toString(){
        return name + " " + score;
    }
}