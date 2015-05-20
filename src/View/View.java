package View;

import javax.swing.JFrame;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import Controller.Controller;
import Model.CommunicationModel;

public abstract class View implements ModelObserver{
	protected JFreeChart pitchGraph,yawGraph,rollGraph;
	protected DefaultCategoryDataset pitchData,yawData,rollData;
	protected static int pitchtime,yawtime,rolltime;
	protected JFrame ventana;
	protected Controller controller;
	protected CommunicationModel model;
}
