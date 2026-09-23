import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class ThemeToggle extends JPanel {
    private boolean isDark = false;
    private final String textOff = "Light";
    private final String textOn = "Dark";
    private Consumer<Boolean> onToggle;

    public ThemeToggle(Consumer<Boolean> onToggle) {
        this.onToggle = onToggle;
        setOpaque(false);
        setPreferredSize(new Dimension(180, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isDark = !isDark;
                repaint();
                if (onToggle != null) {
                    onToggle.accept(isDark);
                }
            }
        });
    }

    public boolean isDark() {
        return isDark;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Background pill
        Color bgCol = new Color(220, 220, 220); // Light Grey
        if (isDark) {
            bgCol = new Color(100, 100, 100); // Darker grey when in dark mode looks better
        }
        g2.setColor(bgCol);
        g2.fillRoundRect(0, 0, w, h, h, h);

        // White slider pill
        g2.setColor(Color.WHITE);
        int arc = h - 6;
        if (isDark) {
            // Slider on right
            g2.fillRoundRect(w / 2, 3, w / 2 - 3, h - 6, arc, arc);
        } else {
            // Slider on left
            g2.fillRoundRect(3, 3, w / 2 - 3, h - 6, arc, arc);
        }

        // Draw text
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        FontMetrics fm = g2.getFontMetrics();

        Color activeText = Color.BLACK;
        Color inactiveText = isDark ? Color.LIGHT_GRAY : Color.GRAY;

        // Left text (Light)
        String leftText = textOff;
        int leftX = (w / 2 - fm.stringWidth(leftText)) / 2;
        int textY = (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(!isDark ? activeText : inactiveText);
        g2.drawString(leftText, leftX, textY);

        // Right text (Dark)
        String rightText = textOn;
        int rightX = w / 2 + (w / 2 - fm.stringWidth(rightText)) / 2;
        g2.setColor(isDark ? activeText : inactiveText);
        g2.drawString(rightText, rightX, textY);
    }
}
