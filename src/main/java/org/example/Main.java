package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car
{
    private String CarId;
    private String brand;
    private String model;
    private int basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, int basePricePerDay) {
        CarId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return CarId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getBasePricePerDay() {
        return basePricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void rent()
    {
        isAvailable = false;
    }
    public  void returnCar()
    {
        isAvailable = true;
    }
    public double calculatePrice(int rentalDays)
    {
        return rentalDays * basePricePerDay;
    }


}
class Customer
{
    private String CustomerId;
    private  String name;

    public Customer(String customerId, String name) {
        CustomerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getName() {
        return name;
    }
}
class Rental
{
    private Car car;
    private Customer customer;
    private int NoOfDays;

    public Rental(Car car, Customer customer, int noOfDays) {
        this.car = car;
        this.customer = customer;
        NoOfDays = noOfDays;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getNoOfDays() {
        return NoOfDays;
    }

}
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int NoOfDays) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, NoOfDays));
        } else {
            System.out.println("Car is not Available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToremove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToremove = rental;
                break;
            }
        }
        if (rentalToremove != null) {
            rentals.remove(rentalToremove);
        } else {
            System.out.println("Car was not rented");
        }

    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + "( " + car.getModel() + " )");
                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), name);
                addCustomer(newCustomer);
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    System.out.print("\nConfirm rental (Y/N): ");
                   // scanner.nextLine();
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }

            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                scanner.nextLine();
                String carId = scanner.nextLine();
                Car returnCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        returnCar = car;
                        break;
                    }
                }
                if (returnCar != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == returnCar) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(returnCar);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }


            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "Accord", 70);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}