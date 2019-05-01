- Built with SpringBoot, Java 8 and GSON lib.
- I have implemented the synchronization by Future Java 8 feature and enablesync Spring annotation. 
- I have used the syncronized at the service to avoid race conditions
  (for example, one thread with 1 event enters the method, runs getevent and stops, just before inserting the event. 
  	Other thread with the same event enters the the method, runs getevent and inserts the event.
  	The first thread would update the event as it was new, without making the calculations of duration and alert flag).
- I have used "timestamp" as "duration" (I have substracted the 2 timestamps) in order to use the same entity for json and db table.
- I have performed the testing suite, they are correct, but in a certain moment I changed something in the test config and I'm not sure what is failing in the configuration. You have suggested not last more than two hours, 
and I'm triying to solve that but i have no more time.
- Personal opinion: I'm not so expert with HSQLDB, and I think installing, setting-up and testing the DB takes a lot of time for newbies, taking into 
account it is supposed not lasting more than 2 hours.   