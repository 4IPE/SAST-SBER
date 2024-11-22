CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS roles_users (
    user_id BIGINT NOT NULL ,
    role_id BIGINT NOT NULL ,
    FOREIGN KEY(role_id) REFERENCES roles(id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    url VARCHAR NOT NULL,
    timeCreate TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS reports (
    id BIGSERIAL PRIMARY KEY,
    file BYTEA NOT NULL ,
    data TIMESTAMP,
    project_id BIGINT NOT NULL ,
    FOREIGN KEY(project_id) REFERENCES projects(id)
);


CREATE TABLE IF NOT EXISTS projects_users (
    project_id BIGINT NOT NULL ,
    user_id BIGINT NOT NULL ,
    FOREIGN KEY(project_id) REFERENCES projects(id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    PRIMARY KEY (project_id, user_id)
);
