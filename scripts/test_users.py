import random
import string
import json

import mysql.connector
from mysql.connector import errorcode

# # Replace with your database connection details
# DB_HOST = "localhost"
# DB_USER = "your_username"
# DB_PASSWORD = "your_password"
# DB_NAME = "membership"


CHARACTERS = [
    "Kermit Frog", "Fozzy Bear", "Miss Piggy", "Gonzo Alien", "Animal Drummer", "Bunsen Honeydew",
    "Beaker Lab", "Scooter Owner", "Lew Zealand", "Statler Waldorf", "Swedish Chef", "Rolf Dog",
    "Pepe Prawn", "Sam Eagle", "Janice Smith", "Animal Drummer", "Cindy Bear", "Floyd Pepper",
    "Zoot Zootie", "Doctor Teeth", "Dick Dastardly", "Muttley Dog", "Scooby Doo",
    "Shaggy Rogers", "Fred Jones", "Daphne Blake", "Velma Dinkley", "Bugs Bunny",
    "Daffy Duck", "Elmer Fudd", "Wile Coyote", "Road Runner", "Tweety Bird",
    "Sylvester Cat", "Marvin Martian", "Yosemite Sam", "Porky Pig", "Foghorn Leghorn"
]

# Function to generate a random email address
def generate_email(name):
    first_name, last_name = name.split(" ")
    return f"{first_name.lower()}.{last_name.lower()}{random.randint(1, 999)}@example.com"

# Function to generate a random password
def generate_password():
    length = 12
    chars = string.ascii_letters + string.digits + string.punctuation
    return ''.join(random.choice(chars) for _ in range(length))

# Function to generate a random address
def generate_address():
    street_names = ["Main St", "Elm St", "Maple St", "Oak St", "Pine St", "Cedar St"]
    cities = ["New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia"]
    states = ["CA", "TX", "FL", "NY", "PA", "IL"]
    return f"{random.choice(street_names)} {random.randint(100, 999)}, {random.choice(cities)}, {random.choice(states)} {random.randint(10000, 99999)}"

# Function to generate a random phone number
def generate_phone_number():
    area_code = random.randint(201, 999)
    prefix = random.randint(100, 999)
    line_number = random.randint(1000, 9999)
    return f"({area_code}) {prefix}-{line_number}"

# Function to create a user record
def create_user(name, group_name="TEST"):
    first_name, last_name = name.split(" ")
    email = generate_email(name)
    password = generate_password()
    address = generate_address()
    city = address.split(", ")[1]
    state = address.split(", ")[2].split()[0]
    phone_number = generate_phone_number()
    title = "Software Engineer" if random.random() > 0.5 else "Product Manager"
    bio = f"I'm a big fan of {random.choice(['Muppet Show', 'cartoons'])}!"
    return {
        "first_name": first_name,
        "last_name": last_name,
        "email": email,
        "password": password,
        "group_name": group_name,
        "address": address,
        "city": city,
        "state": state,
        "phone_number": phone_number,
        "title": title,
        "bio": bio
    }
    
def read_config(filename="db_config.json"):
  """
  Reads database configuration from a JSON file.
  Args:
      filename: Path to the configuration file. Defaults to "db_config.json".
  Returns:
      A dictionary containing the database configuration details (host, user, password, database).
  Raises:
      ValueError: If the configuration file is missing or invalid.
  """
  try:
      with open(filename, "r") as f:
          config = json.load(f)
          return config
  except FileNotFoundError:
      raise ValueError(f"Configuration file '{filename}' not found.")
  except json.JSONDecodeError:
      raise ValueError(f"Invalid JSON format in configuration file '{filename}'.")


# Connect to the database
def insert_users(users):
  
  try:
    # connection = mysql.connector.connect(
    #   host=DB_HOST,
    #   user=DB_USER,
    #   password=DB_PASSWORD,
    #   database=DB_NAME
    # )
    config = read_config()
    connection = mysql.connector.connect(**config)
    cursor = connection.cursor()
    
    sql_insert = "INSERT INTO users (first_name, last_name, email, password, group_name, address, city, state, phone_number, title, bio) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"
    for user in users:
      values = (user["first_name"], user["last_name"], user["email"], user["password"], user["group_name"], user["address"], user["city"], user["state"], user["phone_number"], user["title"], user["bio"])
      
      cursor.execute(sql_insert, values)
      connection.commit()
      print(f"Inserting user {user['first_name']} {user['last_name']}...")

  except mysql.connector.Error as err:
    print("Error connecting to database:", err)
    exit()
  finally:
    connection.close()
    
  
def main():
# User input for output format
  print("Welcome to the user generator!")
  output_format = input("Choose output format (json, sql): ")
    # Validate input format
  if output_format.lower() not in ["json", "sql"]:
    print("Invalid output format. Please choose json or sql.")
    return
  else:
    print(f"Generating users in {output_format} format...")

    # Generate users
  users = [create_user(name) for name in CHARACTERS]

  # Generate output based on chosen format
  if output_format.lower() == "json":
    json_data = json.dumps(users, indent=4)
    print(json_data)
  elif output_format.lower() == "sql":
    # Assuming a table named "users" with appropriate columns
    # Replace with your actual table name and column names
    insert_users(users)

    # Execute when the module is not initialized from an import statement.
if __name__ == "__main__":
    main()