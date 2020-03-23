events:
    AccountCreated
    AccountUpdated

resources:
    accounts (
        id
        password
        name
        email
        contactNumber
        address (
            street
            province
            zip
        )
        phoneNumbers (
            id,
            countryCode,
            phoneNumber,
            dateCreated,
            dateUpdated,
            
        ),
        dateCreated {DATE_TIME<ISO8601>}
        dateUpdated {DATE_TIME<ISO8601>}
    ),
    messages (
        id
        accountId
        to
        from

    )
