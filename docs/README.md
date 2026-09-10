# Computah User Guide

Computah is a desktop chatbot that manages tasks and basic client contact
information through typed commands.

## Managing clients

Client numbers refer to the order shown by `client list`. Phone and email are
optional, and `-` can be used while editing to clear either optional field.

### Adding a client

Use `client add` followed by a name and optional phone or email fields. The
optional fields can appear in either order.

```text
client add Alice Tan
client add Alice Tan /phone +65 9123 4567 /email alice@example.com
client add Bob Lee /email bob@example.com
```

### Listing clients

Use `client list` to show every client in insertion order. A missing phone or
email is displayed as `-`.

```text
client list
```

Example output:

```text
Here are the clients in your list:
1.Alice Tan [phone: +65 9123 4567] [email: alice@example.com]
2.Bob Lee [phone: -] [email: bob@example.com]
```

### Editing a client

Use `client edit` with a client number and at least one field. Only fields in
the command are changed.

```text
client edit 1 /name Alice Lim
client edit 1 /phone +65 9876 5432 /email alice@company.com
```

Clear an optional field using `-`:

```text
client edit 1 /phone -
```

The name cannot be cleared. Repeating a field or using an unknown field causes
the command to be rejected.

### Deleting a client

Use `client delete` with the client's current list number:

```text
client delete 2
```

Deletion takes effect immediately and does not ask for confirmation.

### Client data restrictions

- A client name is required.
- Phone and email values are stored as entered without format validation.
- Leading and trailing whitespace is removed.
- `/name`, `/phone`, and `/email` are reserved field markers and cannot be
  included as text inside a field value.
- Client fields cannot contain line breaks or the text ` | ` because it is
  reserved for the save-file format.
- Multiple clients may have the same details.

Client information is saved in `data/clients.txt` independently of tasks.
