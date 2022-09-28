# Lab 1: Data Structures and Operations on Them

![queue](/screenshots/queue.png)

## 0. Introduction. What is a queue?
A queue is a collection of entities that are maintained in a sequence and can be modified by the addition of entities at one end of the sequence and the removal of entities from the other end of the sequence. 

By convention, the end of the sequence at which elements are added is called the back, tail, or rear of the queue, and the end at which elements are removed is called the head or front of the queue, analogously to the words used when people line up to wait for goods or services. 

## 1. Implementation
All the code is inside `main.c` file. There are 3 general ways to implement a queue with:
- Array
- Linked list
- Stack

I chose linked list. To begin with we have a queue Node with 3 attributes: value, priority and the pointer to the next node.
```
typedef struct Node {
	int value;
    int priority;
	struct Node* next;
} Node;
```
then we have a queue struct from 2 queue node pointers: head and tail.
```
typedef struct Queue {
	Node* head;
	Node* tail;
} Queue;
```
After this we initialize the queue by dynamically allocating memory and return the pointer to the queue
```
Queue* queue_init() {
	Queue* q = (Queue*)malloc(sizeof(Queue));
	q->head = q->tail = NULL;
	return q;
}
```

## 2. Operations
### Enqueue
To enqueue an element it means to add the item to the queue. The priority attribute is set to `MIN_PRIORITY`, because this function is only for normal queue.

```
void enqueue(Queue* q, int val) {
  Node* tmp = (Node*)malloc(sizeof(Node));

	tmp->value = val;
    tmp->priority = MIN_PRIORITY;
	tmp->next = NULL;

	// Empty queue
	if (q->tail == NULL) {
		q->head = q->tail = tmp;
		return;
	}

	q->tail->next = tmp;
	q->tail = tmp;
}
```

### Dequeue
To dequeue an element it means to remove the front/top item from the queue.
```
int dequeue(Queue* q) {
	// Empty queue
	if (isEmpty(q))
		return QUEUE_EMPTY;

	// Store head 
	Node* tmp = q->head;
    int res = tmp->value;

	q->head = q->head->next;

	// if head becomes NULL
	if (q->head == NULL)
		q->tail = NULL;

    // Deallocate the memory
	free(tmp);

    return res;
}
```

### Search
Look for the value entered by user and return the index where it's found
```
int search(Queue* q, int val) {
  // -1 means no item in queue with such value
  int index = 0;

  Node *tmp = q->head;

  if (tmp == NULL) {
    printf("\n>Empty queue. Nothing to search\n");
    return QUEUE_EMPTY;
  }

  while(tmp != NULL){
    if(tmp->value == val) {
      printf("\nValue %d found at index: %d\n", tmp->value, index);
      return index;
    }

    index += 1;
    tmp = tmp->next;
  }

  // if no index is returned it means there is no such value in queue
  printf("\nNo element with value %d in queue\n", val);
  
  return index;
}
```

### Sort
Sort the queue in ascending order by value attribute.
```
void sort(Queue* q, int n) {
  Node **ptr, *tmp;

  if (q->head == NULL){
    printf("\n>Empty queue. Nothing to sort\n");
    return;
  }

  int i;
  // n elements in queue
  for(i = 0; i < n; i++) {
    for(ptr = &q->head; tmp = *ptr; ptr = &(*ptr)->next) {
      Node *other = tmp->next;

      if (!tmp->next) break;
      if (tmp->value < other->value) continue;

      *ptr = other;              
      tmp->next = other->next; 
      other->next = tmp;       
    }
  }  

  return;
}
```

### Reverse
Reverse the queue
```
void reverse(Queue *q) {
  int tmp;
  if (isEmpty(q)) {
    return;
  }

  tmp = dequeue(q);
  reverse(q);
  enqueue(q, tmp);
}
```

## 3. Priority queue
A **priority** queue is an abstract data-type similar to a regular queue or stack data structure in which each element additionally has a priority associated with it. In a priority queue, an element with high priority is served before an element with low priority

```
void p_enqueue(Queue* q, int val, int priority) {
  Node* new_node = malloc(sizeof(Node));

  new_node->value = val;
  new_node->next = NULL;
  new_node->priority = priority;

  if(!isEmpty(q)) {
    if(new_node->priority < q->head->priority) {
      new_node->next = q->head;
      q->head = new_node;
    }
    else {
      Node* prev = q->head;
      Node* tmp = q->head->next;

      while(tmp != NULL && tmp->priority < new_node->priority) {
        prev = tmp;
        tmp = tmp->next;
      }

      if(tmp == NULL) {
        prev->next = new_node;
        q->tail = new_node;
      } else {
        new_node->next = tmp;
        prev->next = new_node;
      }
    }
  } else {
    q->head = new_node;
    q->tail = new_node;
  }
}
```

## 4. Circular queue
A **circular** Queue is a special version of queue where the last element of the queue is connected to the first element of the queue forming a circle

```
void c_enqueue(Queue* q, int val) {
  Node* tmp = (Node*)malloc(sizeof(Node));

	tmp->value = val;
  tmp->priority = MIN_PRIORITY;
	tmp->next = NULL;

	// Empty queue
	if (q->tail == NULL) {
		q->head = q->tail = tmp;
    q->tail->next = q->head;
		return;
	}

	q->tail->next = tmp;
	q->tail = tmp;
  q->tail->next = q->head;
}
```

## 5. Other functionalities
### File handling
There are 3 file pointers:
- f is for writing the file output.txt
- g is for reading the file input.txt
- h is for writing/updating the file input.txt
```
    FILE* g;
    g = fopen("input.txt", "r");

    if(g == NULL) {
      printf("\n>Error: Failed to open the file\n");
    } 

    char queue_data[1000];

    char q_type[9];
    fgets(queue_data, 1000, g);
    strcpy(q_type, queue_data);

    char q_elements[1000];
    while(fgets(queue_data, 1000, g) != NULL) {
      strcpy(q_elements, queue_data);
    } 

    ...
```

### Interactive menu
![menu](/screenshots/menu.png)

It's done simply using an infinite while loop and a switch statement.
```
while (k != 9) {
    printf("\n======== MENU ======== \n");
    printf("| 1 - enqueue        |\n");
    printf("| 2 - dequeue        |\n");
    printf("| 3 - show queue     |\n");
    printf("| 4 - search         |\n");
    printf("| 5 - sort           |\n");
    printf("| 6 - reverse        |\n");
    printf("| 9 - save & exit    |\n");
    printf("======== MENU ======== \n");
    
    scanf("%d", &k);

    switch (k) {
      case 1:
        printf("\n>How many elements to enqueue? \n");
        scanf("%d", &n);
  ...
```

### Display queue
```
void display_queue(Queue* q, int queue_type) {
  Node* tmp = q->head;

  if (tmp == NULL) {
    printf("\n>Empty queue\n");
    return;
  }

  if(queue_type == 2) {
    printf("value(priority): \n");
  } else if(queue_type == 3) {
    printf("Address of q->head: %p\n", q->head);
    printf("Address of q->tail->next: %p\n", q->tail->next);
    while(tmp->next != q->head) {
      printf("%d <~~~ ", tmp->value);
      tmp = tmp->next;
    }
    printf("%d", tmp->value);
    return;
  }

  while(tmp != NULL){
    switch (queue_type) {
    // normal queue
    case 1:
      printf("%d <~~~ ", tmp->value);
      break;

    // priority queue
    case 2:
      printf("%d(%d) <~~~ ", tmp->value, tmp->priority);
      break;

    default:
      printf("\n>Invalid queue type \n");
      break;
    }

    tmp = tmp->next;
  }
}
```