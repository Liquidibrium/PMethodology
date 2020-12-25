
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		list = new ArrayList<>();
		addComponentListener(this);
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		list.clear();
	}

	/* Method: addEntry() */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		list.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		double distance = getWidth() / (double) NDECADES; // distance between vertical lines
		tableLines(distance);
		drawGraphs(distance);
	}

	// draws graphs
	public void drawGraphs(double distance) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < NDECADES; j++) {
				if (j + 1 < NDECADES) {
					line(i, j, distance);
				}
				textLabels(i, j, distance);
			}
		}
	}

	// draws labels at points
	private void textLabels(int i, int j, double distance) {
		GLabel txt = name(i, j);
		txt.setColor(coloring(i));
		add(txt, j * distance + 2, txt.getX() - txt.getAscent() / 2);
	}

	// draws graph's line
	private void line(int i, int j, double distance) {
		GLine line = new GLine(j * distance, name(i, j).getX(), j * distance + distance, name(i, j + 1).getX());
		line.setColor(coloring(i));
		add(line);
	}

	// returns color for graph
	private Color coloring(int x) {
		if (x % 5 == 1) {
			return Color.green;
		} else if (x % 5 == 2) {
			return Color.yellow;
		} else if (x % 5 == 3) {
			return Color.RED;
		} else if (x % 5 == 4) {
			return Color.orange;
		} else
			return Color.BLUE;
	}

	// returns labels of iTH entry's names with it's rank of jTH decade
	// with x position that's y position of graph at jTH decade
	private GLabel name(int i, int j) {
		String name = list.get(i).getName() + " ";
		if (list.get(i).getRank(j) != 0) {
			return new GLabel(name + list.get(i).getRank(j), getHeight() - GRAPH_MARGIN_SIZE
					- (getHeight() - 2 * GRAPH_MARGIN_SIZE) * (MAX_RANK - list.get(i).getRank(j)) / (double) MAX_RANK,
					0);
		}
		return new GLabel(name + " *", getHeight() - GRAPH_MARGIN_SIZE, 0);
	}

	// draws background table
	private void tableLines(double distance) {
		years(distance);
		verticalLines(distance);
		horizonLines();
	}

	// draws labels for years
	private void years(double distance) {
		for (int i = 0; i < NDECADES; i++) {
			String years = " " + (START_DECADE + 10 * i);
			GLabel label = new GLabel(years, i * distance, getHeight() - GRAPH_MARGIN_SIZE / 3.0);
			add(label);
		}
	}

	// draws horizontal lines
	private void horizonLines() {
		GLine topLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(topLine);
		GLine bottomLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(bottomLine);
	}

	// draws vertical lines
	private void verticalLines(double distance) {
		for (int i = 0; i < NDECADES; i++) {
			GLine verticalLine = new GLine(i * distance, 0, i * distance, getHeight());
			add(verticalLine);
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	ArrayList<NameSurferEntry> list;
}
