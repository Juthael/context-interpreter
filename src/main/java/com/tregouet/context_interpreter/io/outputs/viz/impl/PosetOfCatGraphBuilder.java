package com.tregouet.context_interpreter.io.outputs.viz.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.tregouet.context_interpreter.compiler.ICategory;
import com.tregouet.context_interpreter.io.outputs.exceptions.VisualizationException;
import com.tregouet.context_interpreter.io.outputs.viz.IPosetOfCatGraphBuilder;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class PosetOfCatGraphBuilder implements IPosetOfCatGraphBuilder {

	private Graph<String, DefaultEdge> graph;
	
	public PosetOfCatGraphBuilder(){
	}
		
	@Override
	public boolean buildPosetOfCategoriesGraph(Map<ICategory, Set<ICategory>> relationOverCats) 
			throws VisualizationException {
		//build graph
		graph = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		/*
		graph = GraphTypeBuilder
				.<String, DefaultEdge> directed().allowingMultipleEdges(false).allowingSelfLoops(false)
				.edgeClass(DefaultEdge.class).weighted(false).buildGraph(); *
		*/
		for (ICategory category : relationOverCats.keySet()) {
			graph.addVertex(getLabel(category));
		}
		for (Entry<ICategory, Set<ICategory>> entry : relationOverCats.entrySet()) {
			for (ICategory subCategory : entry.getValue()) {
				graph.addEdge(getLabel(entry.getKey()), getLabel(subCategory));
			}
		}
		//convert in DOT format
		DOTExporter<String,DefaultEdge> exporter = new DOTExporter<>();
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(graph, writer);
		String stringDOT = writer.toString();
		/*
		 System.out.println(writer.toString());
		 */
		//display graph
		try {
			MutableGraph dotGraph = new Parser().read(stringDOT);
			Graphviz.fromGraph(dotGraph).width(700).render(Format.PNG).toFile(new File("D:\\ProjetDocs\\essais_viz\\"));
		} catch (IOException e) {
			throw new VisualizationException("PosetOfCatGraphBuilder.buildPosetOfCategoriesGraph("
					+ "Map<ICategory, Set<ICategory>>) : error." + System.lineSeparator() + e.getMessage());
		}
		return true;
	}
	
	private String getLabel(ICategory category) {
		String label = "";
		switch (category.type()) {
			case ICategory.LATT_MIN : 
				label = "Absurdity";
				break;
			case ICategory.LATT_OBJ : 
			case ICategory.LATT_CAT :
				label = category.getExtent().toString();
				break;
			case ICategory.LATT_MAX : 
				label = "Truism";
				break;
			case ICategory.PREACCEPT : 
				label = "PreAccept";
				break;
			case ICategory.ACCEPT : 
				label = "Accept";
				break;
		}
		return label;
	}

}
