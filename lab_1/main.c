#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <string.h>

#define QUEUE_EMPTY INT_MIN
#define MIN_PRIORITY 999

// Linked list
typedef struct Node {
	int value;
  int priority;
	struct Node* next;
} Node;

// Queue 
typedef struct Queue {
	Node* head;
	Node* tail;
} Queue;

// Initialize a queue
Queue* queue_init() {
	Queue* q = (Queue*)malloc(sizeof(Queue));
	q->head = q->tail = NULL;
	return q;
}

// check if queue is empty
int isEmpty(Queue* q) {
  return q->head == NULL;
}

// Get the value of first element
int peek(Queue* q) {
  return q->head->value;
}

int get_n_elements(Queue* q) {
  Node* tmp = q->head;

  int res = 0;
  while(tmp != NULL) {
    res++;
    tmp = tmp->next;
  }

  return res;
}

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

// returns an array with value and priority
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

int c_dequeue(Queue* q) {
  // Empty queue
	if (isEmpty(q))
		return QUEUE_EMPTY;

  Node* tmp = q->head;
  int res = tmp->value;

  if(q->head == q->tail) {
    q->head = q->tail = NULL;
  }
  else {
    q->head = q->head->next;
    q->tail->next = q->head;
  }

  free(tmp);

  return res;
}

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

void queue_export(Queue* q, int queue_type, FILE* f) {
  Node* tmp = q->head;

  if (tmp == NULL) {
    return;
  }

  if(queue_type == 3) {
    while(tmp->next != q->head) {
      if(tmp->value != 0) {
        fprintf(f, "%d ", tmp->value);
      }
      tmp = tmp->next;
    }
    fprintf(f, "%d\n", tmp->value);
    return;
  }

  while(tmp != NULL){
    switch (queue_type) {
    // normal queue
    case 1:
      if(tmp->value != 0) {
        fprintf(f, "%d ", tmp->value);
      }
      break;

    // priority queue
    case 2:
      if(tmp->value != 0) {
        fprintf(f, "%d!%d ", tmp->value, tmp->priority);
      }
      break;

    default:
      printf("\n>Invalid queue type \n");
      break;
    }

    tmp = tmp->next;
  }
  
  fprintf(f, "\n");
}

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

  // while() {
  //   Node *other = tmp->next;

  //   if (!tmp->next) break;
  //   if (tmp->value < other->value) continue;

  //   *ptr = other;              
  //   tmp->next = other->next; 
  //   other->next = tmp;   
  // }

  return;
}

void reverse(Queue *q) {
  int tmp;
  if (isEmpty(q)) {
    return;
  }

  tmp = dequeue(q);
  reverse(q);
  enqueue(q, tmp);
}

void c_reverse(Queue* q) {
    // Temporary helper variables
    Node *prev, *cur, *next, *last;

    // Cannot reverse empty list
    if (isEmpty(q))
    {
      printf("\n>Cannot reverse empty list.\n");
      return;
    }


    // Head is going to be our last node after reversing list
    last = q->head;

    prev  = q->head;
    cur   = q->head->next;
    q->head = q->head->next;

    // Iterate till you reach the initial node in circular list
    while (q->head != last)
    {
      q->head= q->head->next;
      cur->next = prev;

      prev = cur;
      cur  = q->head;
    }

    cur->next = prev;
    q->head = prev;       // Make last node as head
}

int main() {
	Queue* q = queue_init();

  int i, j, n, m, p, k, s;
  /*
    i - loop variable
    j - loop variable
    n - number of elements to enqueue/dequeue
    m - value of element to enqueue/dequeue
    p - priority
    k - interactive menu keyboard variable
    s - search value variable
  */
  int queue_type;

  char queue_type_from_file;
  printf("\n>Read from file? (y / n)\n");
  scanf("%c", &queue_type_from_file);

  if(queue_type_from_file == 'y') {
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

    queue_type = atoi(q_type);

    int q_arr[1000];
    int p_arr[1000];
    char* str, *found;
    // Make a copy of q_elements
    str = strdup(q_elements);
    i = 0;
    while((found = strsep(&str," ")) != NULL) {
      if(queue_type == 2) {
        char* str2, *found2;
        // Make a copy of found
        str2 = strdup(found);
        while((found2 = strsep(&str2,"!")) != NULL) {
          q_arr[i] = atoi(found2);
          i++;
        }
        continue;
      }

      q_arr[i] = atoi(found);
      i++;
    }

    for(j = 0; j < i; j++) {
      switch (queue_type) {
        case 1:
          if(q_arr[j] != 0) 
            enqueue(q, q_arr[j]);
          break;

        case 2:
          if(q_arr[j] != 0) 
            p_enqueue(q, q_arr[j], q_arr[j+1]);
          j++;
          break;
        
        case 3:
          if(q_arr[j] != 0) 
            c_enqueue(q, q_arr[j]);
          break;
        
        default:
          printf("\n>Error: invalid queue type\n");
          break;
      }
    }

    fclose(g);
  } else if(queue_type_from_file == 'n') {
    printf("\n>Type of queue? \n");
    printf("| 1 - normal      |\n");
    printf("| 2 - priority    |\n");
    printf("| 3 - circular    |\n");

    scanf("%d", &queue_type);
    switch (queue_type) {
      case 1:
        printf("Normal queue:      \n");
        break;
      case 2: 
        printf("Priority queue:    \n");
        break;
      case 3: 
        printf("Circular queue:    \n");
        break;
    default:
      printf("\n>Invalid queue type \n");
      break;
    }
  } else {
    printf("\n>Invalid character\n");
  }

  FILE* f;
  f = fopen("output.txt", "w+");

  if(f == NULL) {
    printf("\n>Error: Failed to open the file\n");
  } 

  FILE* h;
  h = fopen("input.txt", "r");

  if(queue_type_from_file == 'y') {
    h = fopen("input.txt", "w");
  }

  // Queue type
  switch (queue_type) {
    case 1:
      fprintf(f, "1\n");
      fprintf(h, "1\n");
      break;
    case 2:
      fprintf(f, "2\n");
      fprintf(h, "2\n");
      break;
    case 3:
      fprintf(f, "3\n");
      fprintf(h, "3\n");
      break;
    default:
      fprintf(f, "invalid\n");
      fprintf(h, "invalid\n");
      break;
  }

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

        for(i = 0; i < n; i++) {
          printf("\n>Value? \n");
          scanf("%d", &m);

          // Priority queue
          if(queue_type == 2) {
            printf("\n>Priority? \n");
            scanf("%d", &p);
            p_enqueue(q, m, p);
            if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
            if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
            continue;
          }

          // Circular queue
          if(queue_type == 3) {
            c_enqueue(q, m);
            queue_export(q, queue_type, f);
            if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
            if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
            continue;
          }

          enqueue(q, m);
          queue_export(q, queue_type, f);
          if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
          if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
        }
        break;
      case 2:
        printf("\n>How many elements to dequeue? \n");
        scanf("%d", &n);

        for(i = 0; i < n; i++) {
          if(queue_type == 3) {
            c_dequeue(q);
            continue;
          }
          dequeue(q);
        }

        queue_export(q, queue_type, f);
        if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
        if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
        break;
      case 3:
        printf("\n");
        display_queue(q, queue_type);
        queue_export(q, queue_type, f);
        if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
        if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
        printf("\n");
        break;
      case 4:
        printf("\n>Element value? \n");
        scanf("%d", &s);
        search(q, s);
        break;
      case 5:
        sort(q, get_n_elements(q));
        queue_export(q, queue_type, f);
        if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
        if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
        printf("\n>Queue was sorted \n");
        break;
      case 6:
        if(queue_type == 3) {
          c_reverse(q);
          queue_export(q, queue_type, f);
          if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
          if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
          printf("\n>Queue was reversed \n");
          break;
        }
        reverse(q);
        queue_export(q, queue_type, f);
        if(queue_type_from_file == 'y') { queue_export(q, queue_type, h); }
        if(queue_type_from_file == 'n') { queue_export(q, queue_type, f); }
        printf("\n>Queue was reversed \n");
        break;
      case 9:
        printf(">Exit complete \n");
        break;
      default:
        printf("\n>Enter a valid value \n");
        break;
    }
  }

  fclose(f);
  if(queue_type_from_file == 'y') {   
    fclose(h);
  }

	return 0;
}
