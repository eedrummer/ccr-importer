# This script will take a directory of CCR's and run them through the preprocessor. It will output
# them into a different directory. It uses the first argument to the script as the directory of CCR's.
# It does not perform any checks to ensure that the document is a CCR, it will only look to make sure
# that the file ends in .xml. It then preprocesses the file and outputs it into the clean directory.
# The clean directory is specified by the second argument passed to the script.

ccr_dir = nil
clean_dir = nil

def chop(path)
  if path[-1] == '/'
    path.chop
  else
    path
  end
end

if ARGV[0] && ARGV[0]
  if File.exists?(ARGV[0])
    ccr_dir = chop(ARGV[0])
  else
    raise "Could not find the directory #{ARGV[0]}"
  end
  clean_dir = chop(ARGV[1])
  Dir.mkdir(clean_dir) unless File.exists?(clean_dir)
else
  raise "Please specify directories for CCR's and where the pre-processed output should be placed"
end

require 'rubygems'
require 'json'
require 'pathname'
require 'java'

Dir.glob('jars/*.jar').each do |jar_file|
  require jar_file
end

import javax.xml.bind.JAXBContext
import java.io.InputStream
import org.ohd.pophealth.preprocess.PreProcessor
import java.io.FileInputStream
import java.io.FileWriter

jc = JAXBContext.new_instance("org.astm.ccr")
unmarshaller = jc.create_unmarshaller()
marshaller = jc.create_marshaller()

pp = PreProcessor.new('umls_db.cfg', 'lvg_db.cfg')

Dir.glob(ccr_dir + '/*.xml').each do |ccr_file|
  ccr = unmarshaller.unmarshal(FileInputStream.new(ccr_file))
  new_ccr = pp.pre_process(ccr)
  fw = FileWriter.new(clean_dir + '/' + File.basename(ccr_file))
  marshaller.marshal(new_ccr, fw)
  fw.close
end