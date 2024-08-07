import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;

public class Manager implements EventListener {
    // Members
    private BufferedImage m_BuffImage;
    private final MainFrame m_ui;

    // Methods
    // Constructor
    public Manager(MainFrame ui) {
        m_BuffImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // Mock Image
        m_ui = ui;
    }

    // Starting point of the program
    public static void main(String[] args) {
        MainFrame mainFrame =  new MainFrame();
        Manager mgr = new Manager(mainFrame);

        // starting the Main frame GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> mainFrame.addEventListener(mgr));
    }

    @Override
    public void handleEvent(EventObject event) {
        switch (event.type) {
            case IMG_UPDATED:
                if (event.obj instanceof BufferedImage) {
                    m_BuffImage = (BufferedImage) event.obj;
                }
                else {
                    System.err.println("Event type: IMG_UPDATED has been caught" +
                            "with wrong parameter object");
                }
                break;
            case RUN_CMD:
                ImageManipulation.invertColor(m_BuffImage);
                m_ui.render(m_BuffImage);
                break;
            default:
                break;
        }
    }
}