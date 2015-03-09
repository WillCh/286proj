# cs286A
General Repo for CS286A projects

*This document under heavy revision*

In the large, the class project is intended to prototype infrastructure for managing metadata and lineage in the Apache Big Data context.  The project should have three phases:
  1. requirements gathering (2 weeks)
  2. functional specification and design (2 weeks) 
  3. prototype implementation (4 weeks)
These phases will likely overlap and have feedback loops.  That's OK.

We envision three basic components:
  1. A *metadata repository* that (a) has a schema to capture relevant information from a set of prototypical tasks and tools, (b) is extensible to new tasks and tools with varying degrees of opacity, and (c) can scale up to large volumes of metadata and high access rates.
  2. A *crawler* that can walk large repositories of information, and call out to an extensible set of external data "recognizers" or "profilers" that can assess the contents of individual data files or sets.  Candidate repositories include HDFS, POSIX filesystems, and relational databases (via standards like JDBC).  The crawler should interface with a standard scheduling infrastructure so it can execute crawls periodically, and drive the crawls at a load-sensitive pace.
  3. A *metadata mover* facility that provides (a) an API for inserting metadata into the repository, (b) a facility for reliable bulk movement of large volumes data into the repository, and (c) an interface to the same scheduling infrastructure as the crawler for executing bulk metadata movement.

