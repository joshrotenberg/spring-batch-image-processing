Example Spring Batch project that processes individual files instead of records in a file.

This implements a single step with a reader that reads files recursively from a directory (in this case src/resources/images),
a processor that base 64 encodes the image data, and a writer that creates a JSON "Request" with the name and encoded data.

I've ommitted the images, but it should run with any files. 

The initial implementation used a custom reader to do most of the work but the good
folks of Spring Batch set me straight with the IteratorItemReader (inline in BatchConfiguration).

