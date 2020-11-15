import imagereader.Runner;

/*
 * A runner runs the image processing program
 */
public final class ImageReaderRunner {
    private ImageReaderRunner() {}
    public static void main(String[] args) {
        ImplementImageIO imageioer = new ImplementImageIO();
        ImplementImageProcessor processor = new ImplementImageProcessor();
        Runner.run(imageioer, processor);
    }
}