db.createUser(
    {
        user: "usrnetflixmongo",
        pwd: "pwdnetflixmongo",
        roles: [
            {
                role: "readWrite",
                db: "netflixdevmongo"
            }
        ]
    }
);