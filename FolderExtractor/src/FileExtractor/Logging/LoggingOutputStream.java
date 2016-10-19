package FileExtractor.Logging;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;

public class LoggingOutputStream extends OutputStream {

    private static final String LINE_SEPERATOR = System.getProperty("line.separator");

    /**
     * The default number of bytes in the buffer. =2048
     */
    private static final int DEFAULT_BUFFER_LENGTH = 2048;

    /**
     * Used to maintain the contract of {@link #close()}.
     */
    private boolean hasBeenClosed = false;

    /**
     * The internal buffer where data is stored.
     */
    private byte[] buf;

    /**
     * The number of valid bytes in the buffer. This value is always in the range <tt>0</tt> through <tt>buf.length</tt>;
     * elements <tt>buf[0]</tt> through <tt>buf[count-1]</tt> contain valid byte data.
     */
    private int count;

    /**
     * Remembers the size of the buffer for speed.
     */
    private int bufLength;

    /**
     * The category to write to.
     */
    private final Category category;

    /**
     * The priority to use when writing to the Category.
     */
    private final Priority priority;

    /**
     * Creates the LoggingOutputStream to flush to the given Category.
     *
     * @param cat the Category to write to
     * @param priority the Priority to use when writing to the Category
     * @exception IllegalArgumentException if cat == null or priority == null
     */
    public LoggingOutputStream(final Category cat, final Priority priority) {
        if (cat == null) {
            throw new IllegalArgumentException("cat == null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("priority == null");
        }

        this.priority = priority;
        this.category = cat;
        this.bufLength = DEFAULT_BUFFER_LENGTH;
        this.buf = new byte[DEFAULT_BUFFER_LENGTH];
        this.count = 0;
    }

    /**
     * Closes this output stream and releases any system resources associated with this stream. The general contract of
     * <code>close</code> is that it closes the output stream. A closed stream cannot perform output operations and cannot be
     * reopened.
     */
    @Override
    public void close() {
        this.flush();
        this.hasBeenClosed = true;
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out. The general contract of
     * <code>flush</code> is that calling it is an indication that, if any bytes previously written have been buffered by the
     * implementation of the output stream, such bytes should immediately be written to their intended destination.
     */
    @Override
    public void flush() {
        if (this.count == 0) {
            return;
        }

        // don't print out blank lines; flushing from PrintStream puts out these
        if (this.count == LINE_SEPERATOR.length()) {
            if ((((char) this.buf[0]) == LINE_SEPERATOR.charAt(0)) && ((this.count == 1) || // <-
            // Unix
            // &
            // Mac,
            // ->
            // Windows
                    ((this.count == 2) && (((char) this.buf[1]) == LINE_SEPERATOR.charAt(1))))) {
                this.reset();
                return;
            }
        }

        final byte[] theBytes = new byte[this.count];

        System.arraycopy(this.buf, 0, theBytes, 0, this.count);

        this.category.log(this.priority, new String(theBytes));

        this.reset();
    }

    private void reset() {
        // not resetting the buffer -- assuming that if it grew that it
        // will likely grow similarly again
        this.count = 0;
    }

    /**
     * Writes the specified byte to this output stream. The general contract for <code>write</code> is that one byte is written
     * to the output stream. The byte to be written is the eight low-order bits of the argument <code>b</code>. The 24
     * high-order bits of <code>b</code> are ignored.
     *
     * @param b the <code>byte</code> to write
     * @exception IOException if an I/O error occurs. In particular, an <code>IOException</code> may be thrown if the output
     *                stream has been closed.
     */
    @Override
    public void write(final int b) throws IOException {
        if (this.hasBeenClosed) {
            throw new IOException("The stream has been closed.");
        }

        // don't log nulls
        if (b == 0) {
            return;
        }

        // would this be writing past the buffer?
        if (this.count == this.bufLength) {
            // grow the buffer
            final int newBufLength = this.bufLength + DEFAULT_BUFFER_LENGTH;
            final byte[] newBuf = new byte[newBufLength];

            System.arraycopy(this.buf, 0, newBuf, 0, this.bufLength);

            this.buf = newBuf;
            this.bufLength = newBufLength;
        }

        this.buf[this.count] = (byte) b;
        this.count++;
    }

}
