# Copy your Java application files (replace with your directory)
COPY . /app

# Expose port (replace 8080 with your desired port)
EXPOSE 8080

# Define command to run within Java container (replace with your command)
CMD ["java", "-jar", "your-app.jar"]

# Define base image for SQL container
FROM mysql:8.0

# Set environment variable for SQL (replace with your desired values)
ENV MYSQL_DATABASE=ecommTask
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_HOST=ecommtask-sql
#MYSQL_HOST:ecommtask-sql -e MYSQL_PORT=3307
#-e MYSQL_DB_NAME=ecommTask -e MYSQL_USER=root -e
# Expose port (replace 3306 with your desired port)
EXPOSE 3306

# Command to run SQL container (don't modify)
CMD ["mysqld"]