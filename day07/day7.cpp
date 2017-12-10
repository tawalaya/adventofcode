#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>

using namespace std;

class INode{
    private:
        string name;
        int wight;
        vector<string> nodes;
    public:

        void print(){

        };

         INode(){
            wight = 0;
            nodes = vector<string>(0);
        };

        INode(string iname,int iwight){
            name = iname;
            wight = iwight;
            nodes = vector<string>(0);
        };
        
        ~INode(){
            nodes.clear();
            nodes.shrink_to_fit();
        }

        void add(string node){
            nodes.push_back(node);
        }

        vector<string> getNodes(){return nodes;}
        int getWight(){return wight;}

        bool isEmpty(){
            
            return nodes.size() <= 0;
        }

        string getName(){return name;}

        string get(int k){
            return nodes[k];
        }

};

class Node{
    private:
        string name;
        int wight;
        vector<Node> nodes;
    public:

        void print(){

        };

         Node(){
            wight = 0;
            nodes = vector<Node>(0);
        };

        Node(string iname,int iwight){
            name = iname;
            wight = iwight;
            nodes = vector<Node>(0);
        };
        
        ~Node(){
            nodes.clear();
            nodes.shrink_to_fit();
        }

        void add(Node node){
            nodes.push_back(node);
        }

        vector<Node> getNodes(){return nodes;}
        int getWight(){return wight;}

        string getName(){return name;}

};

int main() 
{
    //lets parse some stuff
    vector<INode> nodeList = vector<INode>(0);
    ifstream input ("input.txt");
     if (input.is_open()){

        std::string line;
        while (getline(input, line)){
            INode node;

            string header;
            
            string name = line.substr(0,line.find(" ("));
            int wight = stoi(line.substr(line.find("(")+1,line.find(")")));

            //thanks stack overflow!
            node = INode(name,wight);
           
            size_t test = line.find("->");
            if(test!=string::npos){
              
                //children
                stringstream children(line.substr(test+3,line.length()));
                std::string item;
                while (getline(children, item, ',')) {
                    node.add(string(item));
                   
                }
            } 
            nodeList.push_back(node);
          
        }
        input.close();
    }

    vector<Node> processed = vector<Node>();
    vector<INode> blocked = vector<INode>();
    vector<INode> nextLevel = vector<INode>();
    
    for(int i = 0;i<nodeList.size();i++) {
            INode it = nodeList[i];
            //cout << it.getName() << " | " << i << "| " << it.getNodes().size() << endl;
            if(it.getNodes().size() == 0){     
                processed.push_back(Node(it.getName(),it.getWight()));
            } else {
                blocked.push_back(it);
            }
    }

    while(processed.size() < nodeList.size()){        
        //now thats what effiecent code looks like :]
        for(int i = 0;i<blocked.size();i++) {
            INode it = blocked[i];
            Node theNewHope = Node(string(it.getName()),it.getWight());
            for(int j = 0;j<processed.size();j++){
                Node pp = processed[j];
                for(int k = 0;k<it.getNodes().size();k++) {
                    string nn = it.get(k);
                    cout << nn << endl;
                    if(nn.compare(pp.getName())){
                        //yes this looks save af
                        theNewHope.add(pp);
                        break;
                    }
                }
            }
            //cout << it.getName() << " | " << theNewHope.getNodes().size() << "| " << it.getNodes().size() << endl;
            //looks like we found all them nodes that we needed
            if(it.getNodes().size() == theNewHope.getNodes().size()){
                processed.push_back(theNewHope);
            } else {
                nextLevel.push_back(it);
            }
        }        
        blocked = nextLevel ;
        nextLevel = vector<INode>(0);

        cout << blocked.size() << " left " << processed.size() << " done " << endl;
    }
    cout << "done with building that tree :)" << endl;
    
    
    return 0;
}