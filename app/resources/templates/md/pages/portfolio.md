{:title "Portfolio"
 :layout :page
 :navbar? true
 :page-index 200}
 

**Status: Draft written mostly on a plane.**


### HarlanJI.com

* 2018-19

This website serves as the "origin of Harlan's online presence" and exists to replace my presence on "social networking"
sites to the maximum extent possible, using old school Internet and Unix ideas. It's based on iSpooge Live and TinyDC work
(below), using the same technology stack to realize my own vision of internet presence. It lacks the comedy angle to 
a large degree, focusing more on professional presentation. But given screenshot outrage culture, there is no point
using something like a pen name when people can bring it out and use it against me anyway... ie. a pen name is only
a temporary barrier that's good for auxilary functions like marketing. 

Since I use the @harlanji handle on most social sites, I am motivated to build data importers which will then end up
in the upstream iSpooge Live / TinyDC projects and be consumed by HarlanJI.com. It naturally doubles as an installation
of the stack that can validate its usefulness.


### City Apper

* 2018-19
* [Info](https://cityapper.com/about/)
 
City Apper is a play off the connection between hip-hop / old school gangster rap and programming in 2018.


    I never said I was a big app star.
    
    App battles in the park.


The idea stems from leadership in my software engineering career, brought into focus via landscaping and moving work.

It touches important topics such as legal classification, tranining structure, compensation.

    Picture Mark Zuckerberg as the world's most successful cocaine dealer, and build the analogy from there.


### iSpooge Live
 
* 2018
* [Info](https://ispooge.com/fresh.html)

iSpooge Live is a boilerplate website for quickly and easily creating a self-hosted vlog
that syndicates to major providers like Twitch, YouTube, Facebook, and Periscope. It's 
meant for ease-of-creation by folks with little to no development experience, but also
allows one to incrementally learn how to use the parent packages and customize to their 
heart's content. Technically, it incorporates the Sociopathic Media VLOG SDK on top of a 
Tiny DataCenter configuration.

It's been succesfully deployed during a hackathon in which we demo'd a network of three
websites linked together and aggregated into the included RSS reader app, allowing
site creators to build an experience that pulls in videos from affiliated or accessible partner
sites, seamlessly.


See iSpooge Daily 2018 below for details of content.


### Sociopathic Media

* 2018
* [Info](https://ispooge.com/fresh.html)


SpMedia is a video logging (vlogging) software development kit (SDK) that was built as the foundation for 
a personal project meant to capture a difficult period of my life, following examples from other 
vloggers who had impact on my life or built creative formats into their channels, typically
YouTube, but incorporating the live aspect of Twitch follower chat.
 
It incorporates custom scripts that run on top of Tiny DataCenter and VideoJS, following the philosophy
of TDC and giving creators a platform to process their videos imported directly or downloaded from 
their existing youtube channel. It uses industry accepted technology to transcode and segment video
for adaptive rate playback delierable over a CDN with no further computation required.

In otherwords, one can download their YouTube channel into a zip file and upload it to the cheapest
web host imaginable or host it from their raspberry pi utilizing maximal caching in a content delivery
network (CDN)--the secret sauce of silicon valley.

 
 
### Tiny DataCenter

* 2018
* [Info](https://tinydatacenter.com/wwwwwh.html)


TinyDataCenter is an opinionated operation that allows one to run their personal systems from minimal
hardware, using raspberry pi as a reference implementation to prove the capabilities of such a small 
and inexpensive platform. It uses industry accepted Docker to package applications and managed services
between reboots and simple bash scripts in contrast to more complicated provisioning tools such as Ansible,
although much inspiration is drawn from the former we've decided that the target audience is a less experienced
engineer who is just starting to explore building their own systems and wants to get started without such
a barrier to entry. It's structured in such a way that projects can be copy-and-pasted from similar reference projects
that have similar functionality and then customized to meet the wants and needs of the creator, until
more team members are required.

At the point a larger team is required then the creator can ask meaningful questions to the candidate that
they are interviewing and they can make the transition from a simple self-hosted system onto amazon 
web services or similar docker-supporting cloud platforms. 


 
### AddressbookLink

* 2017
* [Info](https://addressbook.link)

AddressBookLink is similar to the now-defunct [Twitter Digits]()) service. Instead of requring a full
upload of contats to a central server, it populates a set data set datastructure called a 
[BloomFilter]())
that supports basic operations that support anonymously exchaning a set of unique values such as 
email addresses and pnone numbers.

The data structure uses deterministic hashing functions that turn a phone number into a distinct value
that can not be reverse engineered from the transferred and stored bytes, but can be used for comparison
by other people who have the same data.

The service was implemented as a [React Native]() app with a small NoteJS REST API backed by a PostgreSQL
service. It was based on an Objective-C prototype that I built called [private-contact-matching]().


### Event Sourcing in Clojure

* 2016
* [Info](https://github.com/harlanji/event-sourcing-clj)

This project was built to experiment with Event Sourcing at the language and platform level.


More coming...

### ClojureSeed

* 2016
* [Info](https://github.com/harlanji/clojureseed)

This project was built to demonstrate a common Clojure code base compiling to front end, back end, and native mobile.

More coming...
 
### Soashable and Xmpp4JS
 
* 2008 
* [Info](http://soashable.sf.net)

Features

* XMPP over Javascripts
* ExtJS Compatible
  * Pluggable class definition layer  
* Anti-Spim 
  * Sends text challenge to new contacts
 
 
 
This project was built after hours while I worked in a union of professional employees as a software engineer
and was limited to 40 hours of work per-week, enforced by a bean counter.

It was a several month project with two major iterations. The first was a proof of concept to see if I could build
a duplicate of a free web app called Meebo using off the shelf software. This succeeded and garnered engouh 
interst among my friends to warrant a more conerted effort. I'd also launched an initiative called 
OSSpace, or Open Source Space as a duplicate of MySpace but decided to build a small subset and focus on
the Instant Messenger aspect. It was based on the ExtJS2 Desktop demo and elaborated to include custom
components and an idiomatically coded Xmpp/BOSH library that was released separately and used as 
a dependency for the messenger project.

It was meant to be a boilerplate for developers to customize and included a package of UI components that 
could be used to represent tree lists of data for ServiceDisco-backed components and a rich chat display.


### PhpServerFaces

* 2006
* [Info](https://sourceforge.net/projects/phpserverfaces)

Having been inspired by Java ServerFaces (JSF) and wanting to bring the joy to my day job that I used PHP 
for I started to port some of the data binding functionality and eventually expanded to implement a good
portion of the original JSF spect in PHP with test cases. 


### FreshSpooge

* 2002
* Lost -- may exist in archive

Fresh2 for short, was created as a common framework that I built to power my blog and e-commerce 
websites that I built for customers. It was modular and supported various lifecycle phases and sets
of modules that could be quickly built into a functional site. It was inspired by PhpNuke and some
of the modules included blogging, encryption, credit card processing, shopping cart, checkout + shipping



### iSpooge Daily

* 2002, 2018
* 2018: [Website](https://ispooge.com)
* 2002 ed. Lost -- may exist in archive

iSpooge Daily 2002 was a project originally built in High School as a Slashdot-like site, for my friends to 
hang out and have fun in our own space. We discussed our dramas and voted on posts, eventally
giving access to my friends via the the uSpooge Daily system via custom subdomains.
 
iSpooge Daily 2k18 is the modern implementation that was originally purchased to retain the archive.org
content of the original, which was blocked by the owner of the domain after my professional 
career began and I let it expire. Since my professional career was abruptly ended and the domain was 
available I decided to continue the project and create a link back to the spirit of original project
to bring back old ways and draw from the energy of nostalgia.


### COSMACS
 
* 1998
* Lost -- may exist in archive

Community Open Source Messaging And Chat System was one of my first major projects that was meant to
replace AOL. It had two iterations beginning in Visual Basic with a custom rounded UI and then ported to a 
C-based backend that gave experience with MySQL and PHP.

It had a buddy list, instant messaging, chat rooms


