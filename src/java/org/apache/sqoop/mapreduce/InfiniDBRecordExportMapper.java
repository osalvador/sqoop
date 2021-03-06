package org.apache.sqoop.mapreduce;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import com.cloudera.sqoop.lib.SqoopRecord;
import org.apache.sqoop.mapreduce.InfiniDBExportMapper;

/**
 * cpimport-based exporter which accepts SqoopRecords (e.g., from
 * SequenceFiles) to emit to the database.
 */
public class InfiniDBRecordExportMapper
    extends InfiniDBExportMapper<LongWritable, SqoopRecord> {

  /**
   * Export the table to InfiniDB by using cpimport to write the data to the
   * database.
   *
   * Expects one SqoopRecord as the value. Ignores the key.
   */
  @Override
  public void map(LongWritable key, SqoopRecord val, Context context)
      throws IOException, InterruptedException {

    writeRecord(val.toString(), null);

    // We don't emit anything to the OutputCollector because we wrote
    // straight to infinidb. Send a progress indicator to prevent a timeout.
    context.progress();
  }
}
