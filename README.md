# JavaPOP3MailClient
  
Version: 1.1

JavaPOP3MailClient is a simple Java desktop application that illustrates how 
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
That means JavaPOP3MailClient will work for some providers and for other it wont
(the second group being the larger one).

This (1.1) release completely obsoletes the previous version so it is advised
not to download that release.

## Usage

Currently JavaPOP3MailClient is not thoroughly tested. Some bugs might occur.

Download the release, extract, add some hosts to hosts.properties file and run
the standalone jar. The properties file must be in the same directory and on
the same level as the jar.

## Should be done...

Proper testing mechanism must be implemented. When this is done, 1.1 release
will be made.

## License

Distributed under GNU GENERAL PUBLIC LICENSE Version 2

Copyright &copy; Dejan Josifović 2014.
