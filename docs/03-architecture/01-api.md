# API

## Описание сущностей:

## User

1. Id
2. Name
3. Phone
4. Email
5. OwnerId(room admin id)

#### Endpoints

CRUD (create, read, update, delete, search)

## Room

1. Id
2. Name
3. Visibility(private/public)
4. OwnerId(room admin id)

#### Endpoints

CRUD (create, read, update, delete, search)

## User-Room

1. UserId
2. RoomId

#### Endpoints

(invite some user, remove some user, leave)

## Message

1. Id
2. Text
3. SenderId
4. GroupId
5. Datetime(время отправки)
6. Read(прочитано ли)

#### Endpoints

WS (create, read, update, delete)