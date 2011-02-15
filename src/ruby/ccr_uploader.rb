require 'rubygems'
require 'rest_client'

def chop(path)
  if path[-1] == '/'
    path.chop
  else
    path
  end
end

ccr_dir = chop(ARGV[0])

Dir.glob(ccr_dir + '/*.xml').each do |ccr_file|
  RestClient.post ARGV[1], :content => File.new(ccr_file)
end