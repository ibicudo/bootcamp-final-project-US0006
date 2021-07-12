#!/usr/bin/env python3

# THIS SCRIPT IS HERE JUST FOR A TITTLE OF DEMONSTRATION

import bcrypt

passwd = b'torresminhopiupiu'
# user/password
# ana meli1705
# fernando calopsitapiupiu2323
# lucas contra123
# ingrid 123456
# ton torresminhopiupiu


salt = bcrypt.gensalt()
hashed = bcrypt.hashpw(passwd, salt)
if bcrypt.checkpw(passwd, hashed):
    print(hashed)
else:
    print("does not match")