import smtplib
from email.mime.text import MIMEText

from email.mime.multipart import MIMEMultipart
import sys

def send_email(sender, reciever, message, subject):
    server = smtplib.SMTP(host = 'smtp.gmail.com', port = 587)
    server.starttls()
    server.login('travlex.tm@gmail.com', 'oxge6048')
    print('Logged into the server')
    
    msg = MIMEMultipart()
    msg['To'] = reciever
    msg['From'] = sender
    msg['Subject' ] = subject
    
    msg.attach(MIMEText(message, 'plain'))
    server.send_message(msg)
    del msg

print('Started executing script')
args = sys.argv[1:]
message = f"Please use this code to reset the password for the Travlex account {args[0]}.\n\nHere is your code : {args[1]}\n\nThanks,\nThe Travlex Account Team"
send_email('travlex.tm@gmail.com', args[0], message, "Travlex Account Password Reset")