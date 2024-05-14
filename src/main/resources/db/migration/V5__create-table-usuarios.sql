
CREATE TABLE usuarios(

    id SERIAL PRIMARY KEY,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(255)  NOT NULL

)