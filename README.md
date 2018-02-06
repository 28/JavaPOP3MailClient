# JavaPOP3MailClient
  
Version: 1.1-SNAPSHOT

*JavaPOP3MailClient* is a simple Java desktop application that illustrates how 
POP3 protocol works. It has an implementation of basic POP3 functions in well 
organized architecture. All code is well documented so it explains how and why 
thing work as they work. Also it has a simple GUI that looks like a mail client
with basic functionalities. The purpose of this project is to show how to
program a simple mail client. 

The application is meant to be used for learning and understanding of POP3
protocol and programming an application that needs to use a certain protocol.
This application illustrates some basic POP3 function that are the core of the 
protocol. To find out all about the POP3 protocol read the 
<a href="http://tools.ietf.org/html/rfc1939">RFC 1939</a> document. This 
document is also used as a sole guide for making this application(a far as 
the protocol goes). Also <a href="http://tools.ietf.org/html/rfc5322">RFC 5322</a>
is consulted for message format (headers etc).

Application only uses bare POP3 protocol that means that it doesn't support MIME 
and/or IMAP. Also it does not implement SMTP although these two always come in 
pair when mail clients are in question. Some/most providers allow connections
only with SSL which has very different configuration even though it uses POP3.
That means *JavaPOP3MailClient* will work for some providers and for other it wont
(the second group being the larger one).

## Usage

**Currently *JavaPOP3MailClient* is not thoroughly tested. Some bugs might occur.**

#### Create an executable
1. Clone the repository: `git clone https://github.com/28/JavaPOP3MailClient.git`;
2. Go to directory: `cd JavaPOP3MailClient`;
3. Run the build script `build.ps1` for Windows or `build.sh` for Unix.

#### Running the program
After the build `out/` directory will be created with the executable jar called
`javapop3mailclient.jar`. In order to run the program `hosts.properties` file must
be in the same directory as the jar. This is also done by the build script.

`hosts.properties` should contain POP3 servers which the client will access for the
given e-mail address. For example this should be the entry for Google Gmail POP3
(assuming that Gmail allows plain text auth) server:
`gmail.com=pop.gmail.com`
This means that for all e-mail addressed with `gmail.com` domain client will try to
connect to `pop.gmail.com`.
With servers set, just run the program.

## Should be done for release 1.1

Proper testing mechanism must be implemented.
Probably <a href="http://www.icegreen.com/greenmail/">GreenMail</a> is the
way to go.

## License

Distributed under GNU GENERAL PUBLIC LICENSE Version 2

Copyright &copy; Dejan Josifović 2014.
