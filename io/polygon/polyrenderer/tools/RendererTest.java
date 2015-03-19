package io.polygon.polyrenderer.tools;

import io.polygon.polyrenderer.tools.AnimationPane;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class RendererTest
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Renderer Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.add(new AnimationPane(), BorderLayout.CENTER);
		frame.setVisible(true);
	}
}