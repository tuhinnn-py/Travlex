import requests
import json
import sys

args = sys.argv[1:]

api_key = 'xC3jXJVUrL0sSSzaMqPsUTSfMJcTPZeXTjykmaft6MW6SBf0A7VF1Nr3S1X7'
url = f'https://www.fast2sms.com/dev/bulkV2'

message = {'sender_id' : 'TRAVLEX', 'message'  : f"Please use this code to reset the password for the Travlex account {args[0]}.\n\nHere is your code : {args[1]}\n\nThanks,\nThe Travlex Account Team", 'language' : 'english', 'route' : 'q', 'numbers' : args[0]}
headers = {'authorization' : api_key, 'Content-Type': "application/x-www-form-urlencoded", 'Cache-Control': "no-cache",}

sms = requests.request("POST", url, data = message, headers = headers)
msg = json.loads(sms.text)

print(msg)