./bin/ollama serve & pid=$!

sleep 5

echo "Pulling llama3.1:latest model"
ollama pull llama3.1:latest

wait $pid
