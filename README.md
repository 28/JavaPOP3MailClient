# JavaPOP3MailClient
  Version: 1.0

JavaPOP3MailClient is a simple Java desktop application that illustrates how POP3 protocol works. 
It has an implementation of basic POP3 functions in well organized architecture. 
All the have documentation that explains how and why thing work as they work. Also it has a simple
GUI that looks like a mail client with basic functionalities. 

The application is meant to be used for learning and understanding of POP3 protocol and 
programming an application that needs to use a certain protocol.

Application only uses bare POP3 protocol that means that it doesn't support MIME and IMAP.
Also it does not implement SMTP although these two always come in pair. Some providers 
allow connections only with SSL which has very different configuration even though it 
uses POP3. That means JavaPOP3MailClient will work for some providers and for other it wont
(the second group being the larger one). But JavaPOP3MailClient isn't meant to work perfectly
even to work at all, it just need to show the basic programming techniques for this
category of problems.

## Usage

1. Install <a href="https://netbeans.org/">NetBeans</a>;
2. Download JavaPOP3MailClient;
3. Load JavaPOP3MailClient project in NetBeans(since it is stored as a NetBeans project).
