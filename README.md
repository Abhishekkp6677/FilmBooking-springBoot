# Film Booking Project
This project is a comprehensive film booking system that allows users to navigate movies, book tickets, select seats from a theater seat layout, and view booking history. It also contains an admin panel for managing movies, theaters, and show timings.

## Database Setup

To set up the database, follow these steps:

1. **Install a database server**: Make sure you have a database server installed (e.g. MySQL).
2. **Create a new database**: Create a database for the project (e.g., `film_booking`).
3. **Configure database connection**: Update the `src/main/resources/application.properties` file with your database connection details. For example:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/film_booking
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
    ```

## Admin Guide
### Admin Login
After cloning the project, you can log in as an admin using the following credentials:
+ Email: admin@gmail.com
+ Password: 12345

### Adding Movies and Theaters
+ Log in as admin.
+ Navigate to the admin panel.
+ Add new movies by providing the required details.
+ Add theaters with their respective details.
+ Add shows and specify the show timings for the movies in the respective theaters.

## User Guide
### Booking a Ticket
+ Navigate through the list of available movies.
+ Select a movie and choose a show timing.
+ Choose seats from the theater seat layout.
+ Confirm the booking.
+ Viewing Booking History
+ Log in to your user account.
+ Navigate to the 'Booking History' section to view all your previous bookings.
