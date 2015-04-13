/* (c) 2014 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */

package gobblin.example.simplejson;

import java.io.IOException;
import java.io.File;

import java.util.List;
import java.util.Iterator;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import gobblin.configuration.ConfigurationKeys;
import gobblin.configuration.SourceState;
import gobblin.configuration.WorkUnitState;
import gobblin.source.Source;
import gobblin.source.extractor.Extractor;
import gobblin.source.workunit.Extract;
import gobblin.source.workunit.WorkUnit;

import org.apache.commons.io.FileUtils;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An implementation of {@link Source} for the simple JSON example.
 *
 * <p>
 *   This source creates one {@link gobblin.source.workunit.WorkUnit}
 *   for each file to pull and uses the {@link SimpleJsonExtractor} to pull the data.
 * </p>
 *
 * @author ynli
 */
@SuppressWarnings("unused")
public class SimpleJsonSource implements Source<String, String> {

  private static final String SOURCE_FILE_KEY = "source.file";

  @Override
  public List<WorkUnit> getWorkunits(SourceState state) {
    List<WorkUnit> workUnits = Lists.newArrayList();

    if (!state.contains(ConfigurationKeys.SOURCE_FILEBASED_FILES_TO_PULL)) {
      return workUnits;
    }

    // Create a single snapshot-type extract for all files
    Extract extract = new Extract(state, Extract.TableType.SNAPSHOT_ONLY,
        state.getProp(ConfigurationKeys.EXTRACT_NAMESPACE_NAME_KEY, "ExampleNamespace"), "ExampleTable");

    String filesToPull = state.getProp(ConfigurationKeys.SOURCE_FILEBASED_FILES_TO_PULL);
    for (String file : Splitter.on(',').omitEmptyStrings().split(filesToPull)) {
      Iterator it = FileUtils.iterateFiles(new File(file), null, true);
      while(it.hasNext()) {
	try{
	File newFile = (File) it.next();
	Path path = newFile.toPath();
	
	// Print filename and associated metadata	
        System.out.println(path);
	BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
	System.out.println("	creationTime: " + attr.creationTime());
	System.out.println("	lastAccessTime: " + attr.lastAccessTime());
	System.out.println("	lastModifiedTime: " + attr.lastModifiedTime());
	System.out.println("	isDirectory: " + attr.isDirectory());
	System.out.println("	isOther: " + attr.isOther());
	System.out.println("	isRegularFile: " + attr.isRegularFile());
	System.out.println("	isSymbolicLink: " + attr.isSymbolicLink());
	System.out.println("	size: " + attr.size());	
      	System.out.println(" ");
    	}catch(IOException e){
    	    e.printStackTrace();
    	}
      }

      System.out.println(" ");
      System.out.println("----END----");

      // Create one work unit for each file to pull
      WorkUnit workUnit = new WorkUnit(state, extract);
      workUnit.setProp(SOURCE_FILE_KEY, file);
      workUnits.add(workUnit);
    }

    return workUnits;
  }

  @Override
  public Extractor<String, String> getExtractor(WorkUnitState state)
      throws IOException {
    return new SimpleJsonExtractor(state);
  }

  @Override
  public void shutdown(SourceState state) {
    // Nothing to do
  }
}
