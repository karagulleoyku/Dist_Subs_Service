require 'socket'
require_relative './Panel/subscriber_pb' # Generated Ruby code from subscriber.proto
require_relative './Panel/capacity_pb'

def main
  servers = [
    { name: "Server1", host: "localhost", port: 5001 },
    { name: "Server2", host: "localhost", port: 5002 },
    { name: "Server3", host: "localhost", port: 5003 }
  ]

  subscriber = Subscriber.new(
    id: 1,
    name_surname: "Odev Deneme",
    status: Subscriber::Status::SUBS
  )

  servers.each do |server|
    begin
      puts "Connecting to #{server[:name]} on port #{server[:port]}..."
      socket = TCPSocket.new(server[:host], server[:port])

      if server[:name] == "Server1"
        data = Subscriber.encode(subscriber)
        socket.write(data)
        puts "Subscriber sent to #{server[:name]}."

      elsif server[:name] == "Server3"
        capacity_request = "CPCTY" 
        socket.puts(capacity_request)
        response = socket.read
        #capacity = Capacity.decode(response)
        puts "Capacity response from #{server[:name]}: #{response.bytes}"

      else
        message = "Ping from Admin to #{server[:name]}"
        socket.puts(message)
        response = socket.gets
        puts "Response from #{server[:name]}: #{response}"
      end

      socket.close
    rescue StandardError => e
      puts "Error connecting to #{server[:name]}: #{e.message}"
    end
  end
end

main if __FILE__ == $PROGRAM_NAME
