package cinema;
import jdk.jfr.Percentage;

import java.util.*;

class MovieTicket {
    int totalTickets;
    int purchasedTickets;
    int currentIncome;
    int totalIncome;

    MovieTicket(int totalTickets, int purchasedTickets, int currentIncome, int totalIncome) {
        this.totalTickets = totalTickets;
        this.purchasedTickets = purchasedTickets;
        this.currentIncome = currentIncome;
        this.totalIncome = totalIncome;
    }
}




public class Cinema {
    public static void createCinemaSeats(char[][] cinemaMatrix){
        for (int i=0; i < cinemaMatrix.length; i++) {
            for (int j = 0; j < cinemaMatrix[0].length; j++) {
                if (i == 0 && j == 0) {
                    cinemaMatrix[i][j] = ' ';
                } else if (i == 0) {
                    cinemaMatrix[i][j] = (char) (j + '0');
                } else if (j == 0) {
                    cinemaMatrix[i][j] = (char) (i + '0');
                } else {
                    cinemaMatrix[i][j] = 'S';
                }
            }
        }
    }

    public static void calculateTotalIncome(MovieTicket ticket, int numRows, int numCols){
        final int ten = 10;
        final int eight = 8;
        int totalSeats = ticket.totalTickets;
        int totalIncome = ten*totalSeats;
        if(totalSeats>60){
            int frontRows = numRows/2;
            int backRows = numRows-frontRows;
            totalIncome = (ten*frontRows*numCols + eight*backRows*numCols);
        }
        ticket.totalIncome = totalIncome;
    }


    public static void showSeats(char[][] cinemaMatrix) {
        System.out.println("\n"+"Cinema:");
        for (int i = 0; i< cinemaMatrix.length; i++) {
            for (int j = 0; j < cinemaMatrix[0].length; j++) {
                System.out.print( cinemaMatrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static boolean checkValidSeat(char[][] cinemaMatrix, int numRows, int numCols, int row, int col){
        if(row<1 || row>numRows || col<1 || col>numCols){
            System.out.println("Wrong input!");
            return false;
        }

        if(cinemaMatrix[row][col] == 'B'){
            System.out.println("That ticket has already been purchased!");
            return false;
        }

        return true;
    }


    public static void buyTicket(char[][] cinemaMatrix, int numRows, int numCols, MovieTicket ticket) {
        Scanner sc = new Scanner(System.in);
        final int ten = 10;
        final int eight = 8;
        int ticketPrice = ten;
        int totalSeats = ticket.totalTickets;
        System.out.println("\n"+"Enter a row number:");
        int ticketBookingRowNum = sc.nextInt();
        System.out.println("Enter a seat number in that row:");
        int ticketBookingRowSeatNum = sc.nextInt();
        if(checkValidSeat(cinemaMatrix, numRows, numCols,ticketBookingRowNum,ticketBookingRowSeatNum)){
            if(totalSeats>60 && ticketBookingRowNum>numRows/2){
                ticketPrice = eight;
            }
            cinemaMatrix[ticketBookingRowNum][ticketBookingRowSeatNum] = 'B';
            ticket.currentIncome+=ticketPrice;
            ticket.purchasedTickets+=1;
            System.out.println("\n"+"Ticket price: "+ "$" +ticketPrice + "\n");
            showSeats(cinemaMatrix);
        }
        else{
            buyTicket(cinemaMatrix,numRows,numCols,ticket);
        }
    }

    public static void showStatistics(MovieTicket ticket){
        float purchasedTicketsRatio = (float)ticket.purchasedTickets/ (float)ticket.totalTickets;
        float purchasedTicketsPercentage = purchasedTicketsRatio*100;
        System.out.println("Number of purchased tickets: "+ ticket.purchasedTickets);
        String str = String.format("Percentage: %.2f%%",purchasedTicketsPercentage);
        System.out.println(str);
        System.out.println("Current Income: $"+ ticket.currentIncome);
        System.out.println("Total income: $"+ ticket.totalIncome);
    }


    public static void main(String[] args) {
        // Write your code here
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numRows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numCols = sc.nextInt();
        int totalSeats = numRows * numCols;
        char[][] cinemaMatrix = new char[numRows+1][numCols+1];
        MovieTicket ticket = new MovieTicket(totalSeats,0,0, 0);
        createCinemaSeats(cinemaMatrix);
        calculateTotalIncome(ticket,numRows,numCols);

        int choice=1;
        while(choice != 0){
            System.out.println("1. Show the seats" );
            System.out.println("2. Buy a ticket" );
            System.out.println("3. Statistics" );
            System.out.println("0. Exit" );

            choice = sc.nextInt();
            switch(choice){
                case 1:
                    showSeats(cinemaMatrix);
                    break;
                case 2:
                    buyTicket(cinemaMatrix, numRows, numCols, ticket);
                    break;
                case 3:
                    showStatistics(ticket);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }
        
    }
}