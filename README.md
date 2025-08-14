# Comunicating with the application in Postman:
1. `POST` `http://localhost:8080/auth/register` :
in Body>raw>JSON send:
```json
{
  "username": "nandones",
  "password": "123456"
}
```
it should return:
```json
{
    "id": 1,
    "username": "nandones"
}
```
the password hash is now saved on the db.

2. Now, chenge the endpoint to
`POST` `http://localhost:8080/auth/login` :
use a valid user-password, like the one above:
```json
{
  "username": "nandones",
  "password": "123456"
}
```
if correct, must return:
```json
{"token":"eyJhbGciOi[...].eyJzdWIiOiJuYW5kb2V[...].WuGM2jMC-wbb[...]"}
```
3. On postman, go to `Authorization`, select `Bearer Token` and paste the token you recieved after the login.
4. Now, to post an Attachment,
Body>form-data;
add the key "file", select File as option, click in "Select files", "+ New file from local machine", and choose a file.
`POST` `http://localhost:8080/attachments` :
it will associate the attachment to the user which the token belongs, and must return:
```json
{
    "id": 1,
    "filename": "519021741_18099635608571299_7360395762126470523_n.jpg",
    "contentType": "image/jpeg"
}
```
5. Now, to search a file:
`GET` `localhost:8080/attachments/{attachmentId}`
if the attchment exists and belongs to the user which owns the current token, it must return:
```json
{
    "id": 1,
    "filename": "519021741_18099635608571299_7360395762126470523_n.jpg",
    "contentType": "image/jpeg"
}
```

6. To search a list of files related to an user:
`GET` `localhost:8080/attachments`
the attchments related to the user which owns the current token will be listede as :
```json
[
    {
        "id": 1,
        "filename": "519021741_18099635608571299_7360395762126470523_n.jpg",
        "contentType": "image/jpeg"
    },
    {
        "id": 2,
        "filename": "40692697_0-1741128919.jpg",
        "contentType": "image/jpeg"
    }
]
```

7. you can delete an attachment using 
`DELETE` `localhost:8080/attachments/{attachmentId}`
if the attchment exists and belongs to the user which owns the current token, it must be deleted.
