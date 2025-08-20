import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ArrayList<String> names=new ArrayList<>();
        ArrayList<Double> scores=new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n=sc.nextInt();sc.nextLine();

        for(int i=0;i<n;i++){
            System.out.print("Enter name of student "+(i+1)+": ");
            names.add(sc.nextLine());
            System.out.print("Enter score for "+names.get(i)+": ");
            scores.add(sc.nextDouble());sc.nextLine();
        }

        double total=0,highest=scores.get(0),lowest=scores.get(0);
        int hi=0,lo=0;
        for(int i=0;i<scores.size();i++){
            double s=scores.get(i);
            total+=s;
            if(s>highest){highest=s;hi=i;}
            if(s<lowest){lowest=s;lo=i;}
        }

        double avg=total/n;
        System.out.println("\n--- Student Grade Report ---");
        for(int i=0;i<names.size();i++)
            System.out.println(names.get(i)+": "+scores.get(i));
        System.out.println("----------------------------");
        System.out.println("Average Score: "+avg);
        System.out.println("Highest Score: "+highest+" (by "+names.get(hi)+")");
        System.out.println("Lowest Score: "+lowest+" (by "+names.get(lo)+")");
    }
}
